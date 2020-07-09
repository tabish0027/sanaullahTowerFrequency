package com.example.murtaza.firebaseproject;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

/**
 * Created by Murtaza on 7/20/2018.
 */

public class BaseStationInfoHelper {
    //android.permission.ACCESS_COARSE_LOCATION
    public static class BaseStationInfo {
        /**
         * Country
         */
        public int mcc = -1;
        /**
         * ISP
         */
        public int mnc = -1;
        /**
         * Base Station Number
         */
        public int lac = -1;
        /**
         * Small Region Number
         */
        public int cid = -1;
    }

    public static BaseStationInfo getSimCardInfo(Context context) {
        final TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        BaseStationInfo res = new BaseStationInfo();
            CellLocation clll =  telephony.getCellLocation();

            if (clll == null) {
                return res;
            }

            if (clll instanceof GsmCellLocation) {
                GsmCellLocation gsm = (GsmCellLocation) clll;
                int lac = gsm.getLac();
                String mcc = telephony.getNetworkOperator().substring(0, 3);
                String mnc = telephony.getNetworkOperator().substring(3);

                res.cid = gsm.getCid();
                res.mcc = Integer.parseInt(mcc);
                res.mnc = Integer.parseInt(mnc);
                res.lac = lac;
            } else if (clll instanceof CdmaCellLocation) {
                CdmaCellLocation cdma = (CdmaCellLocation) clll;
                int lac = cdma.getNetworkId();
                String mcc = telephony.getNetworkOperator().substring(0, 3);
                String mnc = telephony.getNetworkOperator().substring(3);
                int cid = cdma.getBaseStationId();
                res.cid = cid;
                res.mcc = Integer.parseInt(mcc);
                res.mnc = Integer.parseInt(mnc);
                res.lac = lac;
            }

            return res;

        }

    }

