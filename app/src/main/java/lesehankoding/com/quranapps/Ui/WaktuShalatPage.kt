package lesehankoding.com.quranapps.Ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.CalculationParameters
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import lesehankoding.com.quranapps.R
import java.text.SimpleDateFormat
import java.util.*


class WaktuShalatPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waktu_shalat_page)
    }



}


@SuppressLint("SimpleDateFormat")
fun main() {
    val TIME_NAMES = arrayOf("Subuh", "Sunrise", "Dzuhur", "Ashar", "Maghrib", "Isya")
    val coordinates = Coordinates(-6.121435, 106.774124
    )
    val date: DateComponents = DateComponents.from(Date())
    val parameters: CalculationParameters = CalculationMethod.EGYPTIAN.getParameters()
    val formatter = SimpleDateFormat("HH:mm a")
    val prayerTimes = PrayerTimes(coordinates, date, parameters)
    val times = arrayOf<Date>(prayerTimes.fajr, prayerTimes.sunrise, prayerTimes.dhuhr, prayerTimes.asr, prayerTimes.maghrib, prayerTimes.isha)
    for (i in TIME_NAMES.indices) {
        if (TIME_NAMES[i].toLowerCase() != "sunrise") {
            println("\n========================")
            System.out.printf("Waktu Shalat : %s%n", TIME_NAMES[i])
            System.out.printf("Jam Shalat : %s%n", formatter.format(times[i]))
            System.out.printf("TimeMilis Shalat : %s%n", times[i].time)
            println("========================\n")
        }
    }
    println("\nWaktu Shalat Saat Ini : ")
    println("========================")
    System.out.printf("Waktu Shalat : %s%n", prayerTimes.currentPrayer().name)
    System.out.printf("Jam Shalat : %s%n", formatter.format(prayerTimes.timeForPrayer(prayerTimes.currentPrayer())))
    System.out.printf("TimeMilis Shalat : %s%n", prayerTimes.timeForPrayer(prayerTimes.currentPrayer()).getTime())
    println("========================\n")
    println("\nNext Shalat : ")
    println("========================")
    if (prayerTimes.nextPrayer().name == "SUNRISE") {
        System.out.printf("Waktu Shalat : %s%n", TIME_NAMES[2])
        System.out.printf("Jam Shalat : %s%n", formatter.format(times[2]))
        System.out.printf("TimeMilis Shalat : %s%n", times[2].time)
    } else {
        System.out.printf("Waktu Shalat : %s%n", prayerTimes.nextPrayer().name)
        System.out.printf("Jam Shalat : %s%n", formatter.format(prayerTimes.timeForPrayer(prayerTimes.nextPrayer())))
        System.out.printf("TimeMilis Shalat : %s%n", prayerTimes.timeForPrayer(prayerTimes.nextPrayer()).getTime())
    }
    println("========================\n")

}