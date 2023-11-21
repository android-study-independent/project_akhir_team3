package com.example.finalproject_chilicare.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.ForumAdapter
import com.example.finalproject_chilicare.dataclass.ForumData


class HomeFragment : Fragment() {
    private val  listforum = ArrayList<ForumData>()
    private lateinit var  recylerView : RecyclerView
    private lateinit var forumadapter : ForumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recylerView= view.findViewById(R.id.rv_Forum)
        recylerView.setHasFixedSize(true)

        forumadapter = ForumAdapter(listforum)
        recylerView.adapter = forumadapter
        listforum.addAll(getListForum())
        recylerView.layoutManager= LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
    }

    private fun getListForum(): ArrayList<ForumData> {
        val dataavatar = resources.obtainTypedArray(R.array.data_avatar)
        val dataName = resources.getStringArray(R.array.data_name)
        val datadesc = resources.getStringArray(R.array.data_desc)
        val datadatetime = resources.getStringArray(R.array.data_datetime)
        val dataimage = resources.obtainTypedArray(R.array.data_image)
        val listforum = ArrayList<ForumData>()
        for (i in dataName.indices) {
            val user = ForumData(
                dataavatar.getResourceId(i, -1), dataName[i],
                datadatetime[i], datadesc[i], dataimage.getResourceId(i, -1)
            )
            listforum.add(user)
        }

        return listforum

    }


}