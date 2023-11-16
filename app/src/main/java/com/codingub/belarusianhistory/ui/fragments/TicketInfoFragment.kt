package com.codingub.belarusianhistory.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.remote.network.models.tickets.Ticket
import com.codingub.belarusianhistory.databinding.FragmentTicketInfoBinding
import com.codingub.belarusianhistory.ui.base.SharedViewModel
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.custom.dialog.AlertDialog
import com.codingub.belarusianhistory.ui.custom.dialog.AlertDialogView
import com.codingub.belarusianhistory.ui.adapters.TicketInfoAdapter
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketInfoFragment : BaseFragment(){

    private lateinit var binding: FragmentTicketInfoBinding
    private val model: SharedViewModel by activityViewModels()

    private var alertDialog : AlertDialog? = null

    private lateinit var adapter: TicketInfoAdapter

    private var startTime: Long = 0
    private var remainingTime: Long = 0
    private var ticketTime: Long = 0
    private var time: String = ""
    private var handler: Handler? = null

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentTicketInfoBinding.inflate(inf, con, false)

        createButton()
        observeChanges()
         return binding.root
    }

    private fun createButton(){
        binding.btnGoTo.apply {
            typeface = Font.EXTRABOLD
            setOnClickListener {
                if(remainingTime <= 0) pushFragment(MenuFragment(), "menu")
                else showAlertDialog()
            }
        }
    }


    private fun updateTicket(ticket: Ticket){
        binding.tvTicketNumber.apply{
            text = ticket.name
            typeface = Font.EXTRABOLD
        }
        binding.tvTimer.apply{
            text = "3 min"
            typeface = Font.REGULAR
        }


        adapter = TicketInfoAdapter()
        adapter.tickets = ticket.tqs
        binding.rvTicketInfo.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTicketInfo.adapter = adapter

    }


    override fun observeChanges() {
        with(model){
            ticketInfo.observe(viewLifecycleOwner){
                ticketTime = 1*60*1000
                handler = Handler(Looper.myLooper()!!)
                startTime = SystemClock.elapsedRealtime()
                remainingTime = ticketTime

                startTimer()
                updateTicket(it)
            }
        }
    }

    private fun showAlertDialog() {
        if (alertDialog != null) return

        val view = AlertDialogView.Builder(requireContext())
            .message(Resource.string(R.string.back_ticket_info)+time)
            .positiveButton(R.string.yes) {
                pushFragment(MenuFragment(), "menu")
                alertDialog?.dismiss()
            }
            .negativeButton(R.string.no) {
                alertDialog?.dismiss()
            }
            .build()

        alertDialog = AlertDialog(requireContext()).apply {
            setView(view)
            setOnDismissListener {
                alertDialog = null
            }
        }.also { it.show() }
    }

    private fun startTimer() {
        handler?.post(object : Runnable {
            override fun run() {
                val elapsedTime = SystemClock.elapsedRealtime() - startTime
                remainingTime = ticketTime - elapsedTime
                val seconds = (remainingTime / 1000).toInt()
                val minutes = seconds / 60
                time = String.format("%02d:%02d", minutes, seconds % 60)

                if (remainingTime <= 0) {
                    Toast.makeText(requireContext(), Resource.string(R.string.achieve_received), Toast.LENGTH_SHORT).show()
                    return
                }

                handler?.postDelayed(this, 1000) }
        })
    }
}