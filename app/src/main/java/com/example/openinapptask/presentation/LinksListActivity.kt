package com.example.openinapptask.presentation

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openinapptask.R
import com.example.openinapptask.databinding.ActivityLinksListBinding
import com.example.openinapptask.domain.model.LinksListType
import com.example.openinapptask.model.Response
import com.example.openinapptask.presentation.dashboard.DashboardFragment
import com.example.openinapptask.presentation.dashboard.LinksListViewModel
import com.example.openinapptask.presentation.dashboard.adapter.LinksListAdapter

class LinksListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLinksListBinding

    private val linkLiViewModel: LinksListViewModel by viewModels()

    private lateinit var linksListAdapter: LinksListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinksListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        linksListAdapter = LinksListAdapter(onPressedCopy = {
            ViewUtils.copyTextToClipboard(
                it,
                this
            )
        })

        setupAdapter()
        binding.backButton.setOnClickListener { finish() }

        intent.extras?.run {
            val linkLinksListType: LinksListType? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    getSerializable(
                        DashboardFragment.KEY_LINKS_LIST_TYPE,
                        LinksListType::class.java
                    )
                } else {
                    getSerializable(DashboardFragment.KEY_LINKS_LIST_TYPE) as LinksListType
                }
            println("LinkList type from arguments: ${linkLinksListType?.name}")
            onListTabButtonClicked(linkLinksListType!!.toLinkListButtonTypes())
        }

        binding.buttonTopLinks.setOnClickListener { onListTabButtonClicked(LinkListButtonTypes.BUTTON_TOP_LIST) }
        binding.buttonRecentLinks.setOnClickListener { onListTabButtonClicked(LinkListButtonTypes.BUTTON_RECENT_LIST) }
        binding.buttonFavLinks.setOnClickListener { onListTabButtonClicked(LinkListButtonTypes.BUTTON_FAVOURITE_LIST) }

        linkLiViewModel.linksList.observe(this) { linksList ->
            handleResponse(linksList) {
                linksListAdapter.submitList(it)
            }
        }

        linkLiViewModel.listTabButton.observe(this) {
            linkLiViewModel.getLinksList(linkLiViewModel.buttonTypesToLinksListTypes(it))
        }

    }

    private fun onListTabButtonClicked(linkListButtonType: LinkListButtonTypes) {
        println("linklist buttn type ${linkListButtonType.name}")
        handleButtonActive(linkListButtonType)
        linkLiViewModel.onTabButtonClicked(linkListButtonType)
    }

    private fun handleButtonActive(newButtonClickedType: LinkListButtonTypes) {
        println("current selected button ${linkLiViewModel.listTabButton.value}")
        println("new selected button ${newButtonClickedType.name}")
        val currentlySelectedButton: Button =
            getButtonBasedOnType(linkLiViewModel.listTabButton.value!!)
        val newSelectedButton = getButtonBasedOnType(newButtonClickedType)

        ViewUtils.toggleButtonStyle(
            resources = resources,
            newButton = newSelectedButton,
            currentlySelected = currentlySelectedButton,
            activeColor = R.color.black
        )
    }

    private fun getButtonBasedOnType(buttonTypes: LinkListButtonTypes): Button {
        return when (buttonTypes) {
            LinkListButtonTypes.BUTTON_TOP_LIST -> binding.buttonTopLinks
            LinkListButtonTypes.BUTTON_RECENT_LIST -> binding.buttonRecentLinks
            LinkListButtonTypes.BUTTON_FAVOURITE_LIST -> binding.buttonFavLinks
        }
    }

    private fun setupAdapter() {
        val recyclerView = binding.linkCardsList
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = linksListAdapter
    }

    private fun <T> handleResponse(response: Response<T>, onSuccess: (result: T) -> Unit) {
        when (response) {
            is Response.Error -> {
                binding.progressIndicator.visibility = View.GONE
                ViewUtils.showSnackBar(binding.root, response.message)
            }

            Response.Loading -> {
                binding.progressIndicator.visibility = View.VISIBLE
                binding.linkCardsList.visibility = View.GONE
            }

            is Response.Success -> {
                onSuccess(response.result)
                binding.progressIndicator.visibility = View.GONE
                binding.linkCardsList.visibility = View.VISIBLE
            }
        }
    }
}