package com.alokbiswas.alarmmanager

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

const val EXTRA_PESAN = "EXTRA_PESAN"

class MainActivity : AppCompatActivity() {

    val myFormat = "HH:mm"
    private val cal: Calendar = Calendar.getInstance()

    private fun setMyTimeFormat(): String {
        val sdf = SimpleDateFormat(myFormat)
        return sdf.format(cal.getTime())
    }

    private fun setMyDateFormat(): String {
        val myFormat = "dd/MM/yyy"
        val sdf = SimpleDateFormat(myFormat)
        return sdf.format(cal.getTime())
    }

    private fun myTimePicker(): TimePickerDialog.OnTimeSetListener {
        val timeSetListener = object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                tv_setTime.text = setMyTimeFormat()

            }
        }
        return timeSetListener
    }

    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(p0: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            tv_setDate.text = setMyDateFormat()

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val requestCode = 100
        var mPendingIntent: PendingIntent? = null

        btn_setAlarm.setOnClickListener {
            var setTime = Calendar.getInstance()
            var dateTime = Calendar.getInstance()

            setTime.set(Calendar.HOUR_OF_DAY, dateTime[0].toInt())
            setTime.set(Calendar.MINUTE, dateTime[1].toInt())
            setTime.set(Calendar.SECOND, 0)

            val sendIntent = Intent(this, MyAlarmReceiver::class.java)
            sendIntent.putExtra(EXTRA_PESAN, et_message.toString())
            mPendingIntent = PendingIntent.getBroadcast(this, requestCode, sendIntent, 0)
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, setTime.timeInMillis, mPendingIntent)



            Toast.makeText(
                this,
                "Alarm Manager fixed ${btn_setTime.text}:00 with ${btn_setDate.text}",
                Toast.LENGTH_SHORT
            ).show()

        }

        btn_cancelAlarm.setOnClickListener {
            if (mPendingIntent != null) {
                mAlarmManager.cancel(mPendingIntent)
                Toast.makeText(this, "Alarm Manager cancel", Toast.LENGTH_SHORT).show()
            }

        }

        btn_setTime.setOnClickListener { TimePickerDialog(
            this,myTimePicker(),
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show() }

        btn_setDate.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                DatePickerDialog(this@MainActivity,dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()

            }

        })




    }


}

