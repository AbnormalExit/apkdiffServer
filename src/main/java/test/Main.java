package test;

import servers.DiffApkUtils;
import utils.Contacts;

/**
 * Created by Administrator on 2017/12/29 0029.
 */
public class Main {
    public static void main(String args[]){

        DiffApkUtils.diff(Contacts.OLD_FILE,Contacts.NEW_FILE,Contacts.PATCH_FILE);
    }
}
