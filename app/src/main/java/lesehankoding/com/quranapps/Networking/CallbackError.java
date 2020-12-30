package lesehankoding.com.quranapps.Networking;

import android.util.Log;
import android.view.View;

import com.androidnetworking.error.ANError;

public class CallbackError {
    public CallbackError(ANError anError, View.OnClickListener onClickListener) {
        if(anError.getErrorCode() != 0){
            Log.d("CallbackError", "CallbackError: Terjadi kesalahan");
            Log.d("CallbackError", "CallbackError: "+anError.getErrorBody());
        }else{
            Log.d("CallbackError", "CallbackError: Gagal terhubung ke server");
            Log.d("CallbackError", "CallbackError: "+anError.getErrorBody());
        }
    }

    //    public static void errorToast (Context context, ANError anError, String okTitle, View.OnClickListener onClickListener){
//        ModelError modelError = anError.getErrorAsObject(ModelError.class);
//        BottomSheetDialog bottomSheetDialog = null;
//        if(anError.getErrorCode() != 0) {
//            if (anError.getErrorCode() == 401) {
//                if(bottomSheetDialog == null) {
//                    bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
//                    View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottomsheet_401_error, (ConstraintLayout) bottomSheetDialog.findViewById(R.id.bottomsheet_pembayaran_container));
//                    Button btnOk = bottomSheetView.findViewById(R.id.btn_konfirmasi_btm);
//                    btnOk.setText(okTitle);
//                    TextView txt_msg_btm = bottomSheetView.findViewById(R.id.txt_msg_btm);
//                    txt_msg_btm.setText(modelError.getMeta().getMessage());
//                    BottomSheetDialog finalBottomSheetDialog1 = bottomSheetDialog;
//                    btnOk.setOnClickListener(view -> {
//                        finalBottomSheetDialog1.dismiss();
//                        String imei = Prefs.getString(Constans.PREF_IMEI, "");
//                        String oneSignal = Prefs.getString(Constans.PREF_IMEI, "");
//                        Prefs.clear();
//                        Prefs.putString(Constans.PREF_IMEI, imei);
//                        Prefs.putString(Constans.PREF_ONE_SIGNAL, oneSignal);
//                        Intent i = new Intent(context, LoginPage.class);
//                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        ((Activity) context).startActivity(i);
//                        ((Activity) context).finish();
//                    });
//
//
//
//                    bottomSheetDialog.setContentView(bottomSheetView);
//                    bottomSheetDialog.setCancelable(false);
//                    bottomSheetDialog.setCanceledOnTouchOutside(false);
//                    bottomSheetDialog.show();
//                }
//            } else {
//                Toast.makeText(context, modelError.getMeta().getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }else {
//            if(bottomSheetDialog == null) {
//                bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
//                View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottomsheet_inet_error, (ConstraintLayout) bottomSheetDialog.findViewById(R.id.bottomsheet_pembayaran_container));
//                Button btnOk = bottomSheetView.findViewById(R.id.btn_konfirmasi_btm);
//                btnOk.setText(okTitle);
//                Button btnTutup = bottomSheetView.findViewById(R.id.btnTutup);
//                BottomSheetDialog finalBottomSheetDialog = bottomSheetDialog;
//                btnOk.setOnClickListener(view -> {
//                    finalBottomSheetDialog.dismiss();
//                    onClickListener.onClick(view);
//                });
//
//                btnTutup.setOnClickListener(view -> {
//                    ((Activity)context).finish();
//                });
//
//                bottomSheetDialog.setContentView(bottomSheetView);
//                bottomSheetDialog.setCancelable(false);
//                bottomSheetDialog.setCanceledOnTouchOutside(false);
//                bottomSheetDialog.show();
//            }
//        }

//    }


}
