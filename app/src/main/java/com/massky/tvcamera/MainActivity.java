package com.massky.tvcamera;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.InjectView;
import vstc2.nativecaller.NativeCaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ipcamera.demo.BridgeService;
import com.ipcamera.demo.PlayActivity;
import com.ipcamera.demo.utils.ContentCommon;
import com.ipcamera.demo.utils.SystemValue;
import com.massky.domain.entity.weather.WeatherXinZhiEntity;
import com.massky.tvcamera.Utils.DialogUtil;
import com.massky.tvcamera.Utils.SharedPreferencesUtil;
import com.massky.tvcamera.base.BaseActivity;
import com.massky.tvcamera.base.BasePresenter;
import com.massky.tvcamera.di.module.EntityModule;
import com.massky.tvcamera.presenter.HomePresenter;
import com.massky.tvcamera.presenter.contract.HomeContract;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity<BasePresenter> implements  BridgeService.IpcamClientInterface, BridgeService.CallBackMessageInterface {
    private boolean playactivityfinsh = true;
    private String videofrom = "";
    private DialogUtil dialogUtil;
    private Intent intentbrod = null;
    private String strUser;
    private String strDID;
    private String strPwd;
    private Map mapdevice = new HashMap();
    private Map video_item = new HashMap();//来自devicefragment
    private int option = ContentCommon.INVALID_OPTION;
    private int CameraType = ContentCommon.CAMERA_TYPE_MJPEG;
    private static final String STR_DID = "did";
    private static final String STR_MSG_PARAM = "msgparam";
    private boolean blagg = false;
    private MyWifiThread myWifiThread = null;
    private int tag = 0;

    @InjectView(R.id.control_camera)
    Button control_camera;

    @Override
    protected int viewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onView() {
        dialogUtil = new DialogUtil(this);
//        over_camera_list();
        isAppMainProcess();
        init_video();
        init_wifi_camera();
        control_camera();
    }

    @Override
    protected void onEvent() {
        control_camera.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        control_camera();
    }

    /**
     * 去预览
     */
    private void control_camera() {
        mapdevice = new HashMap();
        mapdevice.put("number", "VSTF259167SLNAL");
        mapdevice.put("dimmer", "VSTF259167SLNAL");
        mapdevice.put("deviceId", "");
        mapdevice.put("temperature", "888888");
        mapdevice.put("panelName", "VSTF259167SLNAL");
        mapdevice.put("speed", "admin");
        mapdevice.put("status", "0");
        mapdevice.put("mode", "admin");
        mapdevice.put("mac", "");
        mapdevice.put("name", "摄像头");
        mapdevice.put("panelMac", "VSTF259167SLNAL");
        mapdevice.put("type", "101");
        videofrom = "macfragment";
        onitem_wifi_shexiangtou(mapdevice);
    }

    private boolean again_connection;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            ToastUtil.showToast(MainActivity.this, "mggiz在线");
//            if (dialogUtil != null) dialogUtil.loadDialog();
            switch (msg.what) {
                case 0:
                    runOnUiThread(() -> {
                        if (dialogUtil != null) {
                            dialogUtil.loadDialog();
                        } else {
                            dialogUtil = new DialogUtil(MainActivity.this);
                            dialogUtil.loadDialog();
                        }
                    });
                    break;
                case 3:
                    if (dialogUtil != null) dialogUtil.removeDialog();
                    break;
                case 10://wifi摄像头已经在线了
                    switch (videofrom) {//      videofrom = "macfragment";
                        case "macfragment":
                            mac_fragment_video_ok();
                            break;
                    }
                    break;
                case 11://连接失败，在去连
                    switch (videofrom) {
                        case "macfragment":
                            new Handler().postDelayed(() -> onitem_wifi_shexiangtou(mapdevice),300);
                            break;
                        case "devicefragment":
                            common_video(video_item);
                            break;
                    }
                    break;
            }
        }
    };


    /**
     * 摄像头click
     *
     * @param mapdevice
     */
    private void onitem_wifi_shexiangtou(Map<String, Object> mapdevice) {
        playactivityfinsh = false;
        again_connection = false;
        this.mapdevice = mapdevice;
        common_video(mapdevice);
    }

    /**
     * 共同的视频
     *
     * @param mapdevice
     */
    private void common_video(Map<String, Object> mapdevice) {
        List<Map> list = SharedPreferencesUtil.getInfo_List(MainActivity.this, "list_wifi_camera_first");
        int tag = 0;
        for (int i = 0; i < list.size(); i++) {
            if (mapdevice.get("dimmer").toString()
                    .equals(list.get(i).get("did"))) {
                tag = (int) list.get(i).get("tag");
            }
        }

        if (tag == 1) {

//            in.putExtra(ContentCommon.STR_CAMERA_ID, strDID);
//            in.putExtra(ContentCommon.STR_CAMERA_USER, strUser);
//            in.putExtra(ContentCommon.STR_CAMERA_PWD, strPwd);
//            String strUser, String strPwd, String strDID

            strUser = mapdevice.get("mode").toString();
            strDID = mapdevice.get("dimmer").toString();
            strPwd = mapdevice.get("temperature").toString();
            handler.sendEmptyMessage(10);//设备已经在线了
//            Toast.makeText(MainActivity.this, "设备已经是在线状态了", Toast.LENGTH_SHORT).show();
        } else if (tag == 2) {

            switch (videofrom) {//      videofrom = "macfragment";
                case "macfragment":
                    Toast.makeText(MainActivity.this, "设备不在线", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            done(mapdevice.get("mode").toString()
                    , mapdevice.get("temperature").toString()
                    , mapdevice.get("dimmer").toString());//String strUser,String strPwd,String strDID
        }
    }

    private void done(String strUser, String strPwd, String strDID) {
        Intent in = new Intent();
//        String strUser = userEdit.getText().toString();
//        String strPwd = pwdEdit.getText().toString();
//        String strDID = didEdit.getText().toString();

        if (strDID.length() == 0) {
            Toast.makeText(MainActivity.this,
                    getResources().getString(R.string.input_camera_id), Toast.LENGTH_SHORT).show();
            return;
        } //

        if (strUser.length() == 0) {
            Toast.makeText(MainActivity.this,
                    getResources().getString(R.string.input_camera_user), Toast.LENGTH_SHORT).show();
            return;
        }

        if (option == ContentCommon.INVALID_OPTION) {
            option = ContentCommon.ADD_CAMERA;
        }

        in.putExtra(ContentCommon.CAMERA_OPTION, option);
        in.putExtra(ContentCommon.STR_CAMERA_ID, strDID);
        in.putExtra(ContentCommon.STR_CAMERA_USER, strUser);
        in.putExtra(ContentCommon.STR_CAMERA_PWD, strPwd);
        in.putExtra(ContentCommon.STR_CAMERA_TYPE, CameraType);
//        progressBar.setVisibility(View.VISIBLE);
        if (dialogUtil != null)
            dialogUtil.loadDialog();
        this.strDID = strDID;
        this.strPwd = strPwd;
        this.strUser = strUser;
        SystemValue.deviceName = strUser;
        SystemValue.deviceId = strDID;
        SystemValue.devicePass = strPwd;
        BridgeService.setIpcamClientInterface(this);
        NativeCaller.Init();
        new Thread(new StartPPPPThread()).start();
    }

    /**
     * macfragment video ok
     */
    private void mac_fragment_video_ok() {
        String content = stringbuffer.toString();
        String[] splits = content.split(",");
        if (splits.length == 3) {
            if (splits[0].equals("未知状态") && splits[1].equals("正在连接") && splits[2].equals("在线")) {
                stringbuffer = new StringBuffer();
//                            AppManager.getAppManager().finishActivity_current(PlayActivity.class);
                Intent intent = new Intent("play_finish");
                MainActivity.this.sendBroadcast(intent);
                again_connection = true;
            }
        } else {
            if (!playactivityfinsh) {
//                        AppManager.getAppManager().finishActivity_current(PlayActivity.class);

                SystemValue.deviceName = strUser;
                SystemValue.deviceId = strDID;
                SystemValue.devicePass = strPwd;
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        }
    }


    class StartPPPPThread implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
                startCameraPPPP();
            } catch (Exception e) {

            }
        }
    }

    private MyBroadCast receiver;

    private class MyBroadCast extends BroadcastReceiver {


        @Override
        public void onReceive(Context arg0, Intent arg1) {

//            AddCameraActivity.this.finish();
            Log.d("ip", "AddCameraActivity.this.finish()");
            if (arg1.getAction().equals("finish")) {
                playactivityfinsh = true;
                if (again_connection) {
                    Intent intent_new = new Intent(MainActivity.this, PlayActivity.class);
                    startActivity(intent_new);
                    again_connection = false;
                }
            }
        }
    }


    private void startCameraPPPP() {
        try {
            Thread.sleep(100);
        } catch (Exception e) {

        }

        if (SystemValue.deviceId.toLowerCase().startsWith("vsta")) {
            NativeCaller.StartPPPPExt(SystemValue.deviceId, SystemValue.deviceName,
                    SystemValue.devicePass, 1, "", "EFGFFBBOKAIEGHJAEDHJFEEOHMNGDCNJCDFKAKHLEBJHKEKMCAFCDLLLHAOCJPPMBHMNOMCJKGJEBGGHJHIOMFBDNPKNFEGCEGCBGCALMFOHBCGMFK", 0);
        } else if (SystemValue.deviceId.toLowerCase().startsWith("vstd")) {
            NativeCaller.StartPPPPExt(SystemValue.deviceId, SystemValue.deviceName,
                    SystemValue.devicePass, 1, "", "HZLXSXIALKHYEIEJHUASLMHWEESUEKAUIHPHSWAOSTEMENSQPDLRLNPAPEPGEPERIBLQLKHXELEHHULOEGIAEEHYEIEK-$$", 1);
        } else if (SystemValue.deviceId.toLowerCase().startsWith("vstf")) {
            NativeCaller.StartPPPPExt(SystemValue.deviceId, SystemValue.deviceName,
                    SystemValue.devicePass, 1, "", "HZLXEJIALKHYATPCHULNSVLMEELSHWIHPFIBAOHXIDICSQEHENEKPAARSTELERPDLNEPLKEILPHUHXHZEJEEEHEGEM-$$", 1);
        } else if (SystemValue.deviceId.toLowerCase().startsWith("vste")) {
            NativeCaller.StartPPPPExt(SystemValue.deviceId, SystemValue.deviceName,
                    SystemValue.devicePass, 1, "", "EEGDFHBAKKIOGNJHEGHMFEEDGLNOHJMPHAFPBEDLADILKEKPDLBDDNPOHKKCIFKJBNNNKLCPPPNDBFDL", 0);
        } else if (SystemValue.deviceId.toLowerCase().startsWith("vstg")) {
            NativeCaller.StartPPPPExt(SystemValue.deviceId, SystemValue.deviceName,
                    SystemValue.devicePass, 1, "", "EEGDFHBOKCIGGFJPECHIFNEBGJNLHOMIHEFJBADPAGJELNKJDKANCBPJGHLAIALAADMDKPDGOENEBECCIK:vstarcam2018", 0);
        } else if (SystemValue.deviceId.toLowerCase().startsWith("vstb") || SystemValue.deviceId.toLowerCase().startsWith("vstc")) {
            NativeCaller.StartPPPPExt(SystemValue.deviceId, SystemValue.deviceName,
                    SystemValue.devicePass, 1, "", "ADCBBFAOPPJAHGJGBBGLFLAGDBJJHNJGGMBFBKHIBBNKOKLDHOBHCBOEHOKJJJKJBPMFLGCPPJMJAPDOIPNL", 0);
        } else {
            NativeCaller.StartPPPPExt(SystemValue.deviceId, SystemValue.deviceName,
                    SystemValue.devicePass, 1, "", "", 0);
        }
        //int result = NativeCaller.StartPPPP(SystemValue.deviceId, SystemValue.deviceName,
        //		SystemValue.devicePass,1,"");
        //Log.i("ip", "result:"+result);
    }

    private void stopCameraPPPP() {
        NativeCaller.StopPPPP(SystemValue.deviceId);
    }

    private void init_wifi_camera() {
//        BridgeService.setAddCameraInterface(this);
        BridgeService.setCallBackMessage(this);
        receiver = new MyBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("finish");
        registerReceiver(receiver, filter);
        intentbrod = new Intent("drop");
    }


    private void init_video() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, BridgeService.class);
        startService(intent);
        new Thread(() -> {
            try {
                NativeCaller.PPPPInitialOther("ADCBBFAOPPJAHGJGBBGLFLAGDBJJHNJGGMBFBKHIBBNKOKLDHOBHCBOEHOKJJJKJBPMFLGCPPJMJAPDOIPNL");
                Thread.sleep(3000);
                Message msg = new Message();
                NativeCaller.SetAPPDataPath(getApplicationContext().getFilesDir().getAbsolutePath());
            } catch (Exception e) {

            }
        }).start();
    }

    @Override
    protected void onData() {
//        mPresenter.getWeather("衡阳", "湖南");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.control_camera:
                control_camera();
                break;
        }
    }

//    @Override
//    public void showWeather(WeatherXinZhiEntity.FinalEntity weatherEntity) {
//
//    }

    @Override
    public void showError(String msg) {

    }

//    @Override
//    protected void initInject() {
//        getActivityComponent(new EntityModule())
//                .inject(this);
//    }

    @Override
    public void onStop() {
        super.onStop();
//        if (myWifiThread != null) {
//            blagg = false;
//        }
//        NativeCaller.StopSearch();
    }

    StringBuffer stringbuffer = new StringBuffer();
    private int connection_wifi_camera_index;
    private Handler PPPPMsgHandler = new Handler() {
        public void handleMessage(Message msg) {

            Bundle bd = msg.getData();
            int msgParam = bd.getInt(STR_MSG_PARAM);
            //        bd.putString(STR_DID, did);
//            String  did = bd.getString(STR_DID);
            int msgType = msg.what;
            Log.i("aaa", "====" + msgType + "--msgParam:" + msgParam);
            String did = bd.getString(STR_DID);
            switch (msgType) {
                case ContentCommon.PPPP_MSG_TYPE_PPPP_STATUS:
                    int resid;
                    switch (msgParam) {
                        case ContentCommon.PPPP_STATUS_CONNECTING://0
                            resid = R.string.pppp_status_connecting;
                            Log.e("fei->", "resid:" + "正在连接");
                            if (stringbuffer.toString().contains("未知状态,"))
                                stringbuffer.append("正在连接");
                            tag = 2;
                            if (dialogUtil != null) dialogUtil.removeDialog();
                            break;
                        case ContentCommon.PPPP_STATUS_CONNECT_FAILED://3
                            resid = R.string.pppp_status_connect_failed;
                            Log.e("fei->", "resid:" + "连接失败");
                            tag = 0;
                            if (dialogUtil != null) dialogUtil.removeDialog();
                            connection_wifi_camera_index++;
                            if (connection_wifi_camera_index <= 10)
                                handler.sendEmptyMessage(11);
                            break;
                        case ContentCommon.PPPP_STATUS_DISCONNECT://4
                            resid = R.string.pppp_status_disconnect;
                            Log.e("fei->", "resid:" + "断线");
                            tag = 0;
                            if (dialogUtil != null) dialogUtil.removeDialog();
                            break;
                        case ContentCommon.PPPP_STATUS_INITIALING://1
                            resid = R.string.pppp_status_initialing;
                            Log.e("fei->", "resid:" + "已连接吗，正在初始化");
                            tag = 2;
                            if (dialogUtil != null) dialogUtil.removeDialog();
                            break;
                        case ContentCommon.PPPP_STATUS_INVALID_ID://5
                            resid = R.string.pppp_status_invalid_id;
                            Log.e("fei->", "resid:" + "ID号无效");
//                            progressBar.setVisibility(View.GONE);
                            tag = 0;
                            if (dialogUtil != null) dialogUtil.removeDialog();
                            break;
                        case ContentCommon.PPPP_STATUS_ON_LINE://2 在线状态
                            resid = R.string.pppp_status_online;
                            Log.e("fei->", "resid:" + "在线");
                            connection_wifi_camera_index = 0;
                            if (stringbuffer.toString().contains("未知状态,正在连接"))
                                stringbuffer.append(",在线");
                            //摄像机在线之后读取摄像机类型
                            String cmd = "get_status.cgi?loginuse=admin&loginpas=" + SystemValue.devicePass
                                    + "&user=admin&pwd=" + SystemValue.devicePass;
                            NativeCaller.TransferMessage(did, cmd, 1);
                            tag = 1;
                            if (dialogUtil != null) dialogUtil.removeDialog();
                            handler.sendEmptyMessage(10);//设备已经在线了
                            break;
                        case ContentCommon.PPPP_STATUS_DEVICE_NOT_ON_LINE://6
                            resid = R.string.device_not_on_line;
                            Log.e("fei->", "resid:" + "摄像机不在线");
//                            ToastUtil.showToast(MainActivity.this,"摄像不在线");
                            tag = 0;
                            if (dialogUtil != null) dialogUtil.removeDialog();
                            break;
                        case ContentCommon.PPPP_STATUS_CONNECT_TIMEOUT://7
                            resid = R.string.pppp_status_connect_timeout;
                            Log.e("fei->", "resid:" + "连接超时");
                            tag = 0;
                            if (dialogUtil != null) dialogUtil.removeDialog();
                            break;
                        case ContentCommon.PPPP_STATUS_CONNECT_ERRER://8
                            resid = R.string.pppp_status_pwd_error;
                            Log.e("fei->", "resid:" + "错误密码");
                            tag = 0;
                            if (dialogUtil != null) dialogUtil.removeDialog();
                            break;
                        default:
                            resid = R.string.pppp_status_unknown;
                            Log.e("fei->", "resid:" + "未知状态");
                            stringbuffer = new StringBuffer();
                            stringbuffer.append("未知状态,");
                            if (dialogUtil != null) dialogUtil.removeDialog();
                    }

                    init_Camera(did);

                    /*      textView_top_show.setText(getResources().getString(resid));*/
                    if (msgParam == ContentCommon.PPPP_STATUS_ON_LINE) {
                        NativeCaller.PPPPGetSystemParams(did, ContentCommon.MSG_TYPE_GET_PARAMS);
                    }
                    if (msgParam == ContentCommon.PPPP_STATUS_INVALID_ID
                            || msgParam == ContentCommon.PPPP_STATUS_CONNECT_FAILED
                            || msgParam == ContentCommon.PPPP_STATUS_DEVICE_NOT_ON_LINE
                            || msgParam == ContentCommon.PPPP_STATUS_CONNECT_TIMEOUT
                            || msgParam == ContentCommon.PPPP_STATUS_CONNECT_ERRER) {
                        NativeCaller.StopPPPP(did);
                    }
                    break;
                case ContentCommon.PPPP_MSG_TYPE_PPPP_MODE:
                    break;
            }
        }
    };

    class MyWifiThread extends Thread {
        @Override
        public void run() {
            while (blagg == true) {
                super.run();

                updateListHandler.sendEmptyMessage(100000);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    Handler updateListHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {

            } else {

            }
        }
    };


    /**
     * 初始化摄像头列表
     *
     * @param did
     */
    private void init_Camera(String did) {//修改并完善如果Id相同就更新，没有该Id就添加
        List<Map> list_wifi_camera =
                SharedPreferencesUtil.getInfo_List(MainActivity.this, "list_wifi_camera_first");
        Map map = new HashMap();
        map.put("did", did);
        map.put("tag", tag);
        boolean is_has = false;
        for (int i = 0; i < list_wifi_camera.size(); i++) {
            if (list_wifi_camera.get(i).get("did").equals(did)) {
                list_wifi_camera.get(i).put("tag", tag);
                is_has = true;
                break;
            }
        }

        if (!is_has) {
            list_wifi_camera.add(map);
        }

//        if (index == list_wifi_camera.size()) {
//            list_wifi_camera.add(map);
//        }

//        Map<Integer, Integer> item = list_wifi_camera.get(position);
//        int itemplan = item.entrySet().iterator().next().getValue();
//        int itemplanKey = item.entrySet().iterator().next().getKey();

        SharedPreferencesUtil.saveInfo_List(MainActivity.this, "list_wifi_camera_first", list_wifi_camera);
    }

    @Override
    public void BSMsgNotifyData(String did, int type, int param) {
        Log.d("ip", "type:" + type + " param:" + param);
        Bundle bd = new Bundle();
        Message msg = PPPPMsgHandler.obtainMessage();
        msg.what = type;
        bd.putInt(STR_MSG_PARAM, param);
        bd.putString(STR_DID, did);
        msg.setData(bd);
        PPPPMsgHandler.sendMessage(msg);
        if (type == ContentCommon.PPPP_MSG_TYPE_PPPP_STATUS) {
            intentbrod.putExtra("ifdrop", param);
            sendBroadcast(intentbrod);
        }
    }

    @Override
    public void BSSnapshotNotify(String did, byte[] bImage, int len) {
        // TODO Auto-generated method stub
        Log.i("ip", "BSSnapshotNotify---len" + len);
    }

    @Override
    public void callBackUserParams(String did, String user1, String pwd1,
                                   String user2, String pwd2, String user3, String pwd3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void CameraStatus(String did, int status) {

    }


    @Override
    public void CallBackGetStatus(String did, String resultPbuf, int cmd) {
        // TODO Auto-generated method stub
        if (cmd == ContentCommon.CGI_IEGET_STATUS) {
            String cameraType = spitValue(resultPbuf, "upnp_status=");
            int intType = Integer.parseInt(cameraType);
            int type14 = (int) (intType >> 16) & 1;// 14位 来判断是否报警联动摄像机
            if (intType == 2147483647) {// 特殊值
                type14 = 0;
            }

            if (type14 == 1) {
                updateListHandler.sendEmptyMessage(2);
            }
        }
    }

    private String spitValue(String name, String tag) {
        String[] strs = name.split(";");
        for (int i = 0; i < strs.length; i++) {
            String str1 = strs[i].trim();
            if (str1.startsWith("var")) {
                str1 = str1.substring(4, str1.length());
            }
            if (str1.startsWith(tag)) {
                String result = str1.substring(str1.indexOf("=") + 1);
                return result;
            }
        }
        return -1 + "";
    }

    /**
     * 清除wifi摄像头列表
     */
    private void over_camera_list() {
        List<Map> list = SharedPreferencesUtil.getInfo_List(MainActivity.this, "list_wifi_camera_first");
        List<Map> list_second = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map map = new HashMap();
            map.put("did", list.get(i).get("did"));
            map.put("tag", 0);
            list_second.add(map);
        }
        SharedPreferencesUtil.saveInfo_List(MainActivity.this, "list_wifi_camera_first", new ArrayList<Map>());
        SharedPreferencesUtil.saveInfo_List(MainActivity.this, "list_wifi_camera_first", list_second);
    }

    @Override
    protected void onDestroy() {
//        over_camera_list();//结束wifi摄像头的tag
        super.onDestroy();
    }


    /**
     * 判断是不是UI主进程，因为有些东西只能在UI主进程初始化
     */
    public void isAppMainProcess() {

        int pid = android.os.Process.myPid();//4731
        int pid_past = (int) SharedPreferencesUtil.getData(MainActivity.this, "pid", 0);
        if (pid_past == pid) {//则说明app没有被杀死,直接跳转
            SharedPreferencesUtil.saveData(MainActivity.this, "newProcess", false);
        } else {
            SharedPreferencesUtil.saveData(MainActivity.this, "newProcess", true);
            saveProcess();
            over_camera_list();
            NativeCaller.Free();
        }
    }

    /**
     * 判断是不是UI主进程，因为有些东西只能在UI主进程初始化
     */
    public void saveProcess() {
        int pid = android.os.Process.myPid();//4731
        SharedPreferencesUtil.saveData(MainActivity.this, "pid", pid);
    }

}
