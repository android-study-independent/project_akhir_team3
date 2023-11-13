package com.example.finalproject_chilicare.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.finalproject_chilicare.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TITLE = "title"
private const val ARG_DESC = "desc"
private const val ARG_IMAGE = "image"
class OnboardingFragment : Fragment() {
    private var title: String? = null
    private var desc: String? = null
    private var image: Int? = null

    lateinit var TitleDesc: TextView
    lateinit var Desc: TextView
    lateinit var Image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            desc = it.getString(ARG_DESC)
            image = it.getInt(ARG_IMAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        TitleDesc = view.findViewById(R.id.txttitleonboarding1)
        Desc = view.findViewById(R.id.txtdesconboarding1)
        Image = view.findViewById(R.id.imagetvonboarding1)

        TitleDesc.text=title
        Desc.text= desc
        image?.let {
            Image.setImageResource(it)
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(title: String, desc: String,image : Int) =
            OnboardingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_DESC, desc)
                    putInt(ARG_IMAGE,image)
                }
            }
    }
}