package com.toldrentr.crazyjoker.foo;

import android.content.Context;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.toldrentr.crazyjoker.RatingConfig;

import java.io.IOException;
import java.util.UUID;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class ThreadedIDFAProvider extends AbstractThreadedUtil {

    public static String idfa;
    private final Context mContext;

    public ThreadedIDFAProvider(Context ctx){
        this.mContext = ctx;
    }

    public static String getIdfa(){
        if(idfa == null) return "";
        return idfa;
    }

    @Override
    protected void runPayload() {
        if(RatingConfig.getLocalConfig().contains(Utils.KEY_ADID)){
            idfa = RatingConfig.getLocalConfig().getString(Utils.KEY_ADID, "");
        }
        try {
            AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(mContext);
            if(adInfo!=null&adInfo.getId().length()>0) {
                RatingConfig.getLocalConfig().putString(
                        Utils.KEY_ADID,
                        adInfo.getId()
                );
                idfa = adInfo.getId();
            }else{
                throw new IOException();
            }
        } catch (IOException | GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException exception) {
            idfa = UUID.randomUUID()+"-gen";
            RatingConfig.getLocalConfig().putString(
                    Utils.KEY_ADID,
                    idfa
            );
        }
    }
}