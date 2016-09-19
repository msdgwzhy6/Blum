package com.andreapivetta.blu.ui.privatemessages

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andreapivetta.blu.R
import com.andreapivetta.blu.common.utils.Utils
import com.andreapivetta.blu.data.model.PrivateMessage
import com.andreapivetta.blu.data.storage.AppStorageFactory
import com.andreapivetta.blu.ui.base.custom.decorators.SpaceTopItemDecoration

/**
 * Created by andrea on 28/07/16.
 */
class PrivateMessagesFragment : Fragment(), PrivateMessagesMvpView {

    companion object {
        fun newInstance() = PrivateMessagesFragment()
    }

    private val presenter by lazy { PrivateMessagesPresenter(AppStorageFactory.getAppStorage(context)) }

    private lateinit var adapter: ConversationsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyViewGroup: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
        adapter = ConversationsAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_privatemessages, container, false)

        recyclerView = rootView?.findViewById(R.id.tweetsRecyclerView) as RecyclerView
        emptyViewGroup = rootView?.findViewById(R.id.emptyLinearLayout) as ViewGroup

        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(SpaceTopItemDecoration(Utils.dpToPx(activity, 10)))
        recyclerView.adapter = adapter

        presenter.getConversations()
        return rootView
    }

    override fun showConversations(conversations: MutableList<PrivateMessage>) {
        adapter.dataSet = conversations
        adapter.notifyDataSetChanged()
    }

    override fun showError() {
        emptyViewGroup.visibility = View.VISIBLE
    }

    override fun showEmpty() {
        emptyViewGroup.visibility = View.VISIBLE
    }

}