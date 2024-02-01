package com.example.openinapptask.presentation.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.example.openinapptask.R
import com.example.openinapptask.databinding.FragmentDashboardBinding
import com.example.openinapptask.model.Response
import com.example.openinapptask.presentation.DashboardButtonTypes
import com.example.openinapptask.presentation.LinksListActivity
import com.example.openinapptask.presentation.ViewUtils
import com.example.openinapptask.presentation.toDrawable
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val dashBoardViewModel: DashboardScreenViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpQuickInsightsViews()

        observeData()

        dashBoardViewModel.getScreenData()
        onListTabButtonClicked(DashboardButtonTypes.BUTTON_TOP_LIST)
        binding.buttonTopLinks.setOnClickListener {
            onListTabButtonClicked(DashboardButtonTypes.BUTTON_TOP_LIST)
        }
        binding.buttonRecentLinks.setOnClickListener {
            onListTabButtonClicked(
                DashboardButtonTypes.BUTTON_RECENT_LIST
            )
        }
        binding.viewAllLinks.root.setOnClickListener {
            // go to all links screen
            val intent = Intent(requireContext(), LinksListActivity::class.java)
            intent.putExtra(
                KEY_LINKS_LIST_TYPE,
                dashBoardViewModel.buttonTypesToLinksListTypes(dashBoardViewModel.listTabButton.value!!)
            )
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        dashBoardViewModel.setGreetingBasedOnTime()

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
                    binding.quickInTopLocation.mainText.text = dashboardResult.topLocation
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
                    val card1 = binding.linkCard1
                    val card2 = binding.linkCard2
                    val card3 = binding.linkCard3
                    val card4 = binding.linkCard4

                    card1.linkName.text = list[0].title
                    card1.linkCreationDate.text = list[0].createdAt
                    card1.linkClickCount.text = list[0].totalClicks.toString()
                    card1.smartLinkText.text = list[0].smartLink
                    card1.image.load(list[0].originalImage)
                    card1.bottomLinkShape.setOnClickListener {
                        ViewUtils.copyTextToClipboard(
                            card1.smartLinkText.text.toString(),
                            requireContext()
                        )
                    }

                    card2.linkName.text = list[1].title
                    card2.linkCreationDate.text = list[1].createdAt
                    card2.linkClickCount.text = list[1].totalClicks.toString()
                    card2.smartLinkText.text = list[1].smartLink
                    card2.image.load(list[1].originalImage)
                    card2.bottomLinkShape.setOnClickListener {
                        ViewUtils.copyTextToClipboard(
                            card2.smartLinkText.text.toString(),
                            requireContext()
                        )
                    }


                    card3.linkName.text = list[2].title
                    card3.linkCreationDate.text = list[2].createdAt
                    card3.linkClickCount.text = list[2].totalClicks.toString()
                    card3.smartLinkText.text = list[2].smartLink
                    card3.image.load(list[2].originalImage)
                    card3.bottomLinkShape.setOnClickListener {
                        ViewUtils.copyTextToClipboard(
                            card3.smartLinkText.text.toString(),
                            requireContext()
                        )
                    }


                    card4.linkName.text = list[3].title
                    card4.linkCreationDate.text = list[3].createdAt
                    card4.linkClickCount.text = list[3].totalClicks.toString()
                    card4.smartLinkText.text = list[3].smartLink
                    card4.image.load(list[3].originalImage)
                    card4.bottomLinkShape.setOnClickListener {
                        ViewUtils.copyTextToClipboard(
                            card4.smartLinkText.text.toString(),
                            requireContext()
                        )
                    }

                }
            }
        }

        dashBoardViewModel.listTabButton.observe(viewLifecycleOwner) {
            it?.let { buttonTypes ->
                dashBoardViewModel.getLinksList(
                    dashBoardViewModel.buttonTypesToLinksListTypes(
                        buttonTypes
                    )
                )
            }
        }
    }

    private fun onListTabButtonClicked(types: DashboardButtonTypes) {
        handleButtonActive(types)
        dashBoardViewModel.onTabButtonClicked(types)
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
            is Response.Error -> {
                binding.progressIndicator.visibility = View.GONE
                ViewUtils.showSnackBar(binding.root, response.message)
            }

            Response.Loading -> {
                binding.progressIndicator.visibility = View.VISIBLE
            }

            is Response.Success -> {
                onSuccess(response.result)
                binding.progressIndicator.visibility = View.GONE
                binding.contentView.visibility = View.VISIBLE
            }
        }
    }


    private fun handleButtonActive(newButtonClickedType: DashboardButtonTypes) {
        val currentlySelectedButton: Button? =
            getButtonBasedOnType(dashBoardViewModel.listTabButton.value!!)
        val newSelectedButton = getButtonBasedOnType(newButtonClickedType)

        ViewUtils.toggleButtonStyle(
            resources = resources,
            newButton = newSelectedButton!!,
            currentlySelected = currentlySelectedButton!!,
            activeColor = R.color.primary
        )
    }

    private fun getButtonBasedOnType(buttonTypes: DashboardButtonTypes): Button? {
        return when (buttonTypes) {
            DashboardButtonTypes.BUTTON_TOP_LIST -> binding.buttonTopLinks
            DashboardButtonTypes.BUTTON_RECENT_LIST -> binding.buttonRecentLinks
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

    companion object {
        const val KEY_LINKS_LIST_TYPE = "KEY_LINKS_LIST_TYPE"
    }
}