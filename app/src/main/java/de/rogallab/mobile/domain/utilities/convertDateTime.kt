package de.rogallab.mobile.domain.utilities
import java.time.*
import java.time.format.DateTimeFormatter

/*
   Zulu Time (Coordinated Universal Time) == UTC(GMT) +0
   UTC	2022-06-27T13:22:58Z
   Zoned DateTime(germany)
   Timezone  (Europe/berlin)
*/

/* SHORT_IDS
   EST - -05:00
   HST - -10:00
   MST - -07:00
   ACT - Australia/Darwin
   AET - Australia/Sydney
   AGT - America/Argentina/Buenos_Aires
   ART - Africa/Cairo
   AST - America/Anchorage
   BET - America/Sao_Paulo
   BST - Asia/Dhaka
   CAT - Africa/Harare
   CNT - America/St_Johns
   CST - America/Chicago
   CTT - Asia/Shanghai
   EAT - Africa/Addis_Ababa
   ECT - Europe/Paris
   IET - America/Indiana/Indianapolis
   IST - Asia/Kolkata
   JST - Asia/Tokyo
   MIT - Pacific/Apia
   NET - Asia/Yerevan
   NST - Pacific/Auckland
   PLT - Asia/Karachi
   PNT - America/Phoenix
   PRT - America/Puerto_Rico
   PST - America/Los_Angeles
   SST - Pacific/Guadalcanal
   VST - Asia/Ho_Chi_Minh
 */

val systemZoneId: ZoneId = ZoneId.systemDefault()
var givenZoneId: ZoneId = ZoneId.systemDefault()

val formatZDt: DateTimeFormatter = DateTimeFormatter.ofPattern("eee dd.MM.yyyy - HH:mm:ss:SSS z")
val formatLongDayOfWeek: DateTimeFormatter = DateTimeFormatter.ofPattern("eeee")
val formatShortDayOfWeek: DateTimeFormatter = DateTimeFormatter.ofPattern("EE")
val formatLongDate: DateTimeFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy")
val formatMediumDate: DateTimeFormatter = DateTimeFormatter.ofPattern("d. MMM yyyy")
val formatShortDate: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
val formatShortTime: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
val formatTimeMin: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
val formatTimeSec: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
val formatTimeMs: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
val formatISO: DateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME


// Di., 3. Januar 2023, 10:45 Uhr


fun zonedDateTimeString(zdt: ZonedDateTime) =
   "${zdt.format(formatShortDayOfWeek)} "+
   "${zdt.format(formatMediumDate)} "+
   "${zdt.format(formatTimeMin)} Uhr"

fun zonedDateTimeNow(
   zoneId: ZoneId = givenZoneId
): ZonedDateTime = ZonedDateTime.now(zoneId)

fun zonedDateTimeOf(
   year: Int = LocalDate.now().year,
   month:Int = LocalDate.now().monthValue,
   day: Int = LocalDate.now().dayOfMonth,
   hour: Int = 0,
   min:Int = 0,
   second: Int = 0,
   zoneId: ZoneId = givenZoneId
): ZonedDateTime =
   ZonedDateTime.of(LocalDateTime.of(year,month,day,hour,min,second), givenZoneId)


fun zonedDateTimeAt(
   ldt: LocalDateTime,
   zoneId: ZoneId = givenZoneId
): ZonedDateTime = ZonedDateTime.of(ldt, zoneId)


fun zonedDateTimeAt(
   date: LocalDate,
   time: LocalTime = LocalTime.MIDNIGHT,
   zoneId: ZoneId = givenZoneId
): ZonedDateTime = ZonedDateTime.of(date, time, zoneId)


//- LocalDateTime LocalDate, LocalTime <==> ZonedDateTime ------------
fun toLocalDateTime(zdt: ZonedDateTime): LocalDateTime =
   zdt.toLocalDateTime()
fun toLocalDate(zdt: ZonedDateTime): LocalDate =
   zdt.toLocalDate()
fun toLocalTime(zdt: ZonedDateTime): LocalTime =
   zdt.toLocalTime()
fun toLocalDateTime(date:LocalDate, time:LocalTime): LocalDateTime =
   time.atDate(date)


//- ZonedDateTime <==> Zulu-String, epoch --------------------------
fun toZonedDateTimeUTC(zdt: ZonedDateTime): ZonedDateTime =
   zdt.withZoneSameInstant(ZoneId.of("+0"))

// zonedDateTime -> zuluString
fun toZuluString(zdt: ZonedDateTime): String =
   toZonedDateTimeUTC(zdt).format(formatISO)
// zuluString --> epoch
fun toEpoch(zulu: String): Long =
   Instant.parse(zulu).toEpochMilli()
// ZonedDateTime --> epoch
fun toEpoch(zdt: ZonedDateTime): Long =
   toZonedDateTimeUTC(zdt).toInstant().toEpochMilli()

// epoch -> zuluString
fun toZuluString(epoch: Long): String =
   Instant.ofEpochMilli(epoch).atZone(ZoneId.of("+0")) // zdtUTC
      .format(formatISO)
// zuluString --> zonedDateTime
fun toZonedDateTime(zulu: String): ZonedDateTime =
   ZonedDateTime.parse(zulu, formatISO)
                .withZoneSameInstant(givenZoneId)

// epoch --> ZonedDateTime
fun toZonedDateTime(epoch: Long): ZonedDateTime {
   val instant = Instant.ofEpochMilli(epoch);
   return ZonedDateTime.ofInstant(instant, givenZoneId)
}