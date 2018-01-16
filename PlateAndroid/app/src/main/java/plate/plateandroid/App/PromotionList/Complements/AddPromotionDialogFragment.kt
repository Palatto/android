package plate.plateandroid.App.PromotionList.Complements

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import plate.plateandroid.App.PromotionList.PromotionModel
import plate.plateandroid.R
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by rennerll on 12/13/17.
 */
class AddPromotionDialogFragment: DialogFragment() {
    var cancelButton: Button? = null
    var concludeButton: Button? = null
    var promotionTitle: EditText? = null
    var promotionLocation: EditText? = null
    var promotionStartTime: EditText? = null
    var promotionEndTime: EditText? = null
    var promotionListDialogInterface: PromotionListDialogInterface? = null

    var calendarStartTime = Calendar.getInstance()
    var startTimeDatePickerInterface = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        calendarStartTime.set(Calendar.YEAR, year)
        calendarStartTime.set(Calendar.MONTH, monthOfYear)
        calendarStartTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val myFormat = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        promotionStartTime?.setText(sdf.format(calendarStartTime.getTime()))

        val startTimeTimePicker = TimePickerDialog.newInstance(startTimeTimePickerInterface, 0, 0, true)
        startTimeTimePicker.setAccentColor(ContextCompat.getColor(context!!, R.color.mainRed))
        startTimeTimePicker.setCancelColor(ContextCompat.getColor(context!!, android.R.color.darker_gray))
        startTimeTimePicker.setOkColor(ContextCompat.getColor(context!!, R.color.mainBlue))
        startTimeTimePicker.show(this@AddPromotionDialogFragment.activity?.fragmentManager, START_TIME_PICKER)
    }
    var startTimeTimePickerInterface = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute, second ->
        calendarStartTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendarStartTime.set(Calendar.MINUTE, minute)
        calendarStartTime.set(Calendar.SECOND, second)

        val myFormat = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        promotionStartTime?.setText(sdf.format(calendarStartTime.getTime()))
    }

    var calendarEndTime = Calendar.getInstance()
    var endTimeDatePickerInterface = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        calendarEndTime.set(Calendar.YEAR, year)
        calendarEndTime.set(Calendar.MONTH, monthOfYear)
        calendarEndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val myFormat = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        promotionEndTime?.setText(sdf.format(calendarEndTime.getTime()))

        val endTimeTimePicker = TimePickerDialog.newInstance(endTimeTimePickerInterface, 0, 0, true)
        endTimeTimePicker.setAccentColor(ContextCompat.getColor(context!!, R.color.mainRed))
        endTimeTimePicker.setCancelColor(ContextCompat.getColor(context!!, android.R.color.darker_gray))
        endTimeTimePicker.setOkColor(ContextCompat.getColor(context!!, R.color.mainBlue))
        endTimeTimePicker.show(this@AddPromotionDialogFragment.activity?.fragmentManager, END_TIME_PICKER)
}
    var endTimeTimePickerInterface = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute, second ->
        calendarEndTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendarEndTime.set(Calendar.MINUTE, minute)
        calendarEndTime.set(Calendar.SECOND, second)

        val myFormat = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        promotionEndTime?.setText(sdf.format(calendarEndTime.getTime()))
    }

    companion object {
        val START_DATE_PICKER = "startDatePicker"
        val END_DATE_PICKER = "endDatePicker"
        val START_TIME_PICKER = "startTimePicker"
        val END_TIME_PICKER = "endTimePicker"

        fun newInstance(): AddPromotionDialogFragment {
            val fragment = AddPromotionDialogFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var view: View? = inflater.inflate(R.layout.fragment_dialog_add_promotion, container)
        bindView(view)

        promotionTitle!!.requestFocus()
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return view
    }

    fun bindView(view: View?) {
        promotionTitle = view?.findViewById<EditText>(R.id.promotionTitle)

        promotionStartTime = view?.findViewById<EditText>(R.id.promotionStartTime)
        promotionStartTime?.setOnClickListener {
            val calendarMin = Calendar.getInstance()
            val calendarMax = Calendar.getInstance()
            calendarMax.add(Calendar.MONTH, 1)

            val startTimeDatePicker = DatePickerDialog.newInstance(startTimeDatePickerInterface, calendarStartTime.get(Calendar.YEAR), calendarStartTime.get(Calendar.MONTH), calendarStartTime.get(Calendar.DAY_OF_MONTH))
            startTimeDatePicker.setAccentColor(ContextCompat.getColor(context!!, R.color.mainRed))
            startTimeDatePicker.setCancelColor(ContextCompat.getColor(context!!, android.R.color.darker_gray))
            startTimeDatePicker.setOkColor(ContextCompat.getColor(context!!, R.color.mainBlue))
            startTimeDatePicker.minDate = calendarMin
            startTimeDatePicker.maxDate = calendarMax
            startTimeDatePicker.show(this@AddPromotionDialogFragment.activity?.fragmentManager, START_DATE_PICKER)
        }

        promotionEndTime = view?.findViewById<EditText>(R.id.promotionEndTime)
        promotionEndTime?.setOnClickListener {
            val calendarMin = Calendar.getInstance()
            calendarMin.time = calendarStartTime.time
            val calendarMax = Calendar.getInstance()
            calendarMax.time = calendarStartTime.time
            calendarMax.add(Calendar.DAY_OF_MONTH, 7)

            calendarEndTime.time = calendarStartTime.time

            val endTimeDatePicker = DatePickerDialog.newInstance(endTimeDatePickerInterface, calendarEndTime.get(Calendar.YEAR), calendarEndTime.get(Calendar.MONTH), calendarEndTime.get(Calendar.DAY_OF_MONTH))
            endTimeDatePicker.setAccentColor(ContextCompat.getColor(context!!, R.color.mainRed))
            endTimeDatePicker.setCancelColor(ContextCompat.getColor(context!!, android.R.color.darker_gray))
            endTimeDatePicker.setOkColor(ContextCompat.getColor(context!!, R.color.mainBlue))
            endTimeDatePicker.minDate = calendarMin
            endTimeDatePicker.maxDate = calendarMax
            endTimeDatePicker.show(this@AddPromotionDialogFragment.activity?.fragmentManager, END_DATE_PICKER)
        }

        promotionLocation = view?.findViewById<EditText>(R.id.promotionLocation)

        cancelButton = view?.findViewById<Button>(R.id.cancelItemQuantityButton)
        cancelButton?.setOnClickListener {
            promotionListDialogInterface?.dismissAddPromotionDialogFragment(false, null)
        }

        concludeButton = view?.findViewById<Button>(R.id.concludeItemQuantityButton)
        concludeButton?.setOnClickListener {
            if(!promotionTitle?.text.isNullOrEmpty() && !promotionLocation?.text.isNullOrEmpty() && !promotionStartTime?.text.isNullOrEmpty() && !promotionStartTime?.text.isNullOrEmpty()) {
                val promotionModel = PromotionModel("", promotionTitle!!.text.toString(), promotionStartTime!!.text.toString(), promotionEndTime!!.text.toString(), promotionLocation!!.text.toString())
                promotionListDialogInterface?.dismissAddPromotionDialogFragment(true, promotionModel)
            }else {
                promotionListDialogInterface?.dismissAddPromotionDialogFragment(true, null)
            }
        }
    }
}