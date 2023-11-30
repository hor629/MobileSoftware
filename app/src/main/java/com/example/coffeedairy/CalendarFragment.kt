package com.example.coffeedairy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.coffeedairy.databinding.ActivityCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.Collections

class CalendarFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ActivityCalendarBinding.inflate(layoutInflater, container, false)
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            activity.let{
                val intent = Intent(context, DateLogActivity::class.java)
                startActivity(intent)
            }

            // binding.calendarView.addDecorator(EventDecorator(Collections.singleton(date)))
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //outState.putStringArrayList("datas", ArrayList(datas))
    }

}

class EventDecorator(dates: Collection<CalendarDay>) : DayViewDecorator {
    val dates: HashSet<CalendarDay> = HashSet(dates)
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(ForegroundColorSpan(Color.GREEN))
    }
}
