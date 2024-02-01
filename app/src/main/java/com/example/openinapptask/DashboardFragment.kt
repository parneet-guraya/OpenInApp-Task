package com.example.openinapptask

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openinapptask.data.DashboardRepositoryImpl
import com.example.openinapptask.data.remote.DashboardRemoteDataSourceImpl
import com.example.openinapptask.data.remote.network.OpenInAppApi
import com.example.openinapptask.databinding.FragmentDashboardBinding
import com.example.openinapptask.model.Response
import com.example.openinapptask.presentation.dashboard.DashboardScreenViewModel
import com.example.openinapptask.presentation.dashboard.LinksListType
import com.example.openinapptask.presentation.dashboard.adapter.LinksListAdapter
import com.example.openinapptask.presentation.toDrawable
import com.example.openinapptask.presentation.updateButtonStyle
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val dashboardRepository =
        DashboardRepositoryImpl(DashboardRemoteDataSourceImpl(OpenInAppApi.openInAppApiService))
    private val dashBoardViewModel: DashboardScreenViewModel by viewModels<DashboardScreenViewModel> {
        DashboardScreenViewModel.Companion.DashboardViewModelFactory(
            dashboardRepository
        )
    }
    private lateinit var linksListAdapter: LinksListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linksListAdapter = LinksListAdapter(onPressedCopy = {copyTextToClipboard(it)})
        setUpQuickInsightsViews()

        setupAdapter()

        observeData()

        dashBoardViewModel.getScreenData()
        dashBoardViewModel.getLinksList(LinksListType.TOP)
        binding.buttonTopLinks.setOnClickListener { dashBoardViewModel.getLinksList(LinksListType.TOP) }
        binding.buttonRecentLinks.setOnClickListener { dashBoardViewModel.getLinksList(LinksListType.RECENT) }

    }

    override fun onStart() {
        super.onStart()
        dashBoardViewModel.setGreetingBasedOnTime()
    }

    private fun copyTextToClipboard(text: String){
        val clipboard = getSystemService(requireContext(),ClipboardManager::class.java)
        clipboard?.let {
            val clip: ClipData = ClipData.newPlainText("Copied:", text)
            it.setPrimaryClip(clip)
        }

    }

    private fun observeData() {

        dashBoardViewModel.greetingLiveData.observe(viewLifecycleOwner) {
            binding.greetingsText.text = it
        }

        dashBoardViewModel.screenData.observe(viewLifecycleOwner) {
            it?.let { dashboardResponse ->
                handleResponse(dashboardResponse) { dashboardResult ->
                    binding.quickInTodayClicks.mainText.text =
                        dashboardResult.todayClicks.toString()
                    binding.quickInTopLocation.mainText.text =
                        dashboardResult.topLocation
                    binding.quickInTopSource.mainText.text = dashboardResult.topSource
                    val monthToValuePair =
                        convertToMonthNumberList(dashboardResult.data.overallUrlChart)

                    binding.chartView.setModel(entryModelOf(monthToValuePair))
                }
            }
        }

        dashBoardViewModel.linksList.observe(viewLifecycleOwner) {
            it?.let { linksList ->
                handleResponse(linksList) { list ->
                    linksListAdapter.submitList(list)
                }
            }
        }

        dashBoardViewModel.listTabButton.observe(viewLifecycleOwner) {
            it?.let { linkListType ->
                handleButtonActive(linkListType)
            }
        }
    }

    private fun convertToMonthNumberList(inputMap: Map<String, Int>): List<FloatEntry> {
        // TODO: need to handle on old android version
        return inputMap.map { (dateString, value) ->
            val monthNumber =
                LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE).monthValue
            FloatEntry(monthNumber.toFloat(), value.toFloat())
        }
    }

    private fun <T> handleResponse(response: Response<T>, onSuccess: (result: T) -> Unit) {
        when (response) {
            is Response.Error -> {}
            Response.Loading -> {}
            is Response.Success -> onSuccess(response.result)
        }
    }

    private fun setupAdapter() {
        val recyclerView = binding.linkCardsList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = linksListAdapter
    }

    private fun handleButtonActive(linksListType: LinksListType) {
        when (linksListType) {
            LinksListType.RECENT -> {
                binding.buttonRecentLinks.updateButtonStyle(true)
                binding.buttonTopLinks.updateButtonStyle(false)
            }

            LinksListType.TOP -> {
                binding.buttonRecentLinks.updateButtonStyle(false)
                binding.buttonTopLinks.updateButtonStyle(true)
            }

            else -> {}
        }
    }

    private fun setUpQuickInsightsViews() {
        binding.quickInTodayClicks.iconFrame.background =
            R.drawable.today_clicks_icon_bg.toDrawable(resources)
        binding.quickInTodayClicks.iconSrcStub.setImageResource(R.drawable.today_clicks_icon)
        binding.quickInTodayClicks.subText.text = "Today Clicks"

        binding.quickInTopLocation.iconFrame.background =
            R.drawable.top_location_icon_bg.toDrawable(resources)
        binding.quickInTopLocation.iconSrcStub.setImageResource(R.drawable.top_location_icon)
        binding.quickInTopLocation.subText.text = "Top Location"

        binding.quickInTopSource.iconFrame.background =
            R.drawable.top_source_icon_bg.toDrawable(resources)
        binding.quickInTopSource.iconSrcStub.setImageResource(R.drawable.top_source_icon)
        binding.quickInTopSource.subText.text = "Top Source"
    }

}