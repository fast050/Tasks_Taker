package com.example.tasklisttaker.utils.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.SyncStateContract
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tasklisttaker.R
import com.example.tasklisttaker.ui.detail.DetailTaskActivity
import com.example.tasklisttaker.ui.main.TaskListActivity
import com.example.tasklisttaker.utils.constant.Constants


const val Channel_Id= "5";
fun notify(context: Context, title:String?=null, description:String?=null, icon:Int?= null, notificationId:Int?=null)
{

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        createChannel(context, Channel_Id,context.getString(R.string.app_name))

    val notification = createNotification(context
        ,title ?: "title" ,
        description ?: "description",
        icon ?: R.drawable.ic_baseline_alarm_add_24)

    with(NotificationManagerCompat.from(context)) {
        // notificationId is a unique int for each notification that you must define
        notify(notificationId ?: 1
            , notification)
    }

}

private fun createNotification(context: Context,title: String,description: String,icon: Int): Notification
{
    //open the activity from other app
    val intent = Intent(context,TaskListActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_TASK_ON_HOME
    }
    val pendingIntent = PendingIntent.getActivity(context,
        Constants.NOTIFICATION_PEND_INTENT_REQ_CODE, intent,PendingIntent.FLAG_UPDATE_CURRENT)


    return NotificationCompat.Builder(context, Channel_Id)
        .setContentTitle(title)
        .setContentIntent(pendingIntent)
        .setContentText(description)
        .setAutoCancel(true)
        .setSmallIcon(icon)
        .build()

}


@RequiresApi(Build.VERSION_CODES.O)
private fun createChannel(context:Context, channelId:String, name:String, importance:Int = NotificationManager.IMPORTANCE_DEFAULT)
{
    val channel = NotificationChannel(channelId,name,importance)
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}