package cn.sx.decentworld.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cn.sx.decentworld.R;

/**
 * 注册页
 * 
 */
public class RegisterActivity extends BaseFragmentActivity {
	private EditText userNameEditText;
	private EditText passwordEditText;
	private EditText confirmPwdEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		userNameEditText = (EditText) findViewById(R.id.username);
		passwordEditText = (EditText) findViewById(R.id.password);
		confirmPwdEditText = (EditText) findViewById(R.id.confirm_password);
	}

	/**
	 * 注册
	 * 
	 * @param view
	 */
	// public void register(View view)
	// {
	// final String username = userNameEditText.getText().toString().trim();
	// final String pwd = passwordEditText.getText().toString().trim();
	// String confirm_pwd = confirmPwdEditText.getText().toString().trim();
	// if (TextUtils.isEmpty(username))
	// {
	// Toast.makeText(this,
	// getResources().getString(R.string.User_name_cannot_be_empty),
	// Toast.LENGTH_SHORT).show();
	// userNameEditText.requestFocus();
	// return;
	// } else if (TextUtils.isEmpty(pwd))
	// {
	// Toast.makeText(this,
	// getResources().getString(R.string.Password_cannot_be_empty),
	// Toast.LENGTH_SHORT).show();
	// passwordEditText.requestFocus();
	// return;
	// } else if (TextUtils.isEmpty(confirm_pwd))
	// {
	// Toast.makeText(this,
	// getResources().getString(R.string.Confirm_password_cannot_be_empty),
	// Toast.LENGTH_SHORT).show();
	// confirmPwdEditText.requestFocus();
	// return;
	// } else if (!pwd.equals(confirm_pwd))
	// {
	// Toast.makeText(this,
	// getResources().getString(R.string.Two_input_password),
	// Toast.LENGTH_SHORT).show();
	// return;
	// }
	//
	// if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd))
	// {
	// final ProgressDialog pd = new ProgressDialog(this);
	// pd.setMessage(getResources().getString(R.string.Is_the_registered));
	// pd.show();
	//
	// new Thread(new Runnable()
	// {
	// public void run()
	// {
	// try
	// {
	// // 调用sdk注册方法
	// EMChatManager.getInstance().createAccountOnServer(username, pwd);
	// runOnUiThread(new Runnable()
	// {
	// public void run()
	// {
	// if (!RegisterActivity.this.isFinishing())
	// pd.dismiss();
	// // 保存用户名
	// DecentWorldApp.getInstance().setUserName(username);
	// Toast.makeText(getApplicationContext(),
	// getResources().getString(R.string.Registered_successfully), 0).show();
	// finish();
	// }
	// });
	// } catch (final EaseMobException e)
	// {
	// runOnUiThread(new Runnable()
	// {
	// public void run()
	// {
	// if (!RegisterActivity.this.isFinishing())
	// pd.dismiss();
	// int errorCode = e.getErrorCode();
	// if (errorCode == EMError.NONETWORK_ERROR)
	// {
	// Toast.makeText(getApplicationContext(),
	// getResources().getString(R.string.network_anomalies),
	// Toast.LENGTH_SHORT).show();
	// } else if (errorCode == EMError.USER_ALREADY_EXISTS)
	// {
	// Toast.makeText(getApplicationContext(),
	// getResources().getString(R.string.User_already_exists),
	// Toast.LENGTH_SHORT).show();
	// } else if (errorCode == EMError.UNAUTHORIZED)
	// {
	// Toast.makeText(getApplicationContext(),
	// getResources().getString(R.string.registration_failed_without_permission),
	// Toast.LENGTH_SHORT).show();
	// } else if (errorCode == EMError.ILLEGAL_USER_NAME)
	// {
	// Toast.makeText(getApplicationContext(),
	// getResources().getString(R.string.illegal_user_name),
	// Toast.LENGTH_SHORT).show();
	// } else
	// {
	// Toast.makeText(getApplicationContext(),
	// getResources().getString(R.string.Registration_failed) + e.getMessage(),
	// Toast.LENGTH_SHORT).show();
	// }
	// }
	// });
	// }
	// }
	// }).start();
	//
	// }
	// }

	public void back(View view) {
		finish();
	}

}
