package com.moez.QKSMS.common.utils;

import com.moez.QKSMS.ui.messagelist.MessageItem;

/**
 * Created by kautsar on 04/12/17.
 */

public class CryptoUtils {

    public String hcrypt(String input) {

        char[] key = {'K', 'S' , 'R'};
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            output.append((char) (input.charAt(i) ^ key[i % key.length]));
        }

        return output.toString();
    }
}
