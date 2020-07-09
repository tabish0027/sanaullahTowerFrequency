package com.example.murtaza.firebaseproject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Murtaza on 8/1/2018.
 */

public final class ReflectionUtils {
    //  public static final String TAG = ReflectionUtils.class.getSimpleName();

    /**
     * Dumps a {@link Class}'s {@link Method}s and {@link Field}s
     * as a String.
     */
    public static final String dumpClass(Class<?> mClass, Object mInstance) {
        if (mClass == null || mInstance == null) return null;

        String mStr = mClass.getSimpleName() + "\n\n";
        //String mStr+"\n\n";

        mStr += "FIELDS\n\n";

        final Field[] mFields = mClass.getDeclaredFields();

        for (final Field mField : mFields) {
            mField.setAccessible(true);

            mStr += mField.getName() + " (" + mField.getType() + ") = ";

            try {
                mStr += mField.get(mInstance).toString();
            } catch (Exception e) {
                mStr += "null";
                // Log.e(TAG, "Could not get Field `" + mField.getName() + "`.", e);

            }

            mStr += "\n";
        }



        return mStr;
    }


}
