package com.example.biaeweverton.projetowb.files.Views;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.media.app.NotificationCompat;

import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Controllers.MainController;
import com.example.biaeweverton.projetowb.files.Models.Account;
import com.example.biaeweverton.projetowb.files.Models.Deck;
import com.example.biaeweverton.projetowb.files.Models.MainControllerInterface;

import java.util.ArrayList;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel(context.getString(R.string.default_notification_channel_id), "Channel_Notification", NotificationManager.IMPORTANCE_HIGH));
        }
        MainController mainController = new MainController(context);
        mainController.getDeckList(context, Account.userId, new MainControllerInterface() {
            @Override
            public void onCompleteSave(Boolean res) {

            }

            @Override
            public void onLoadQuantityDataToStudy(int quantity) {

            }

            @Override
            public void onLoadingDeck(ArrayList<Deck> listDeck) {
                String msg = "";
                if(listDeck.size() == 0){
                    msg = "Você ainda não possui nenhum Deck, crie um é comece a estudar!";
                }else{
                    msg = "Você tem " + listDeck.size() + " Decks, não esqueça de estudar hoje!";
                }
                android.support.v4.app.NotificationCompat.Builder notification = new android.support.v4.app.NotificationCompat.Builder(context,context.getString(R.string.default_notification_channel_id)).setSmallIcon(R.drawable.ic_play).setContentTitle("Easy Learn").setContentText(msg);
                notificationManager.notify(1, notification.build());
            }
        });
    }
}
