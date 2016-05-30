package cn.sx.decentworld.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.OccupationAdapter;
import cn.sx.decentworld.adapter.ProfessionAdapter;
import cn.sx.decentworld.adapter.UniversityAdapter;
import cn.sx.decentworld.bean.OccupationBean;
import cn.sx.decentworld.component.ToastComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_occupation)
public class OccupationActivity extends BaseFragmentActivity {
	@ViewById(R.id.lv_profession)
	ListView lvProfession;
	@ViewById(R.id.lv_occupation)
	ListView lvOccupation;
	@ViewById(R.id.act_university)
	AutoCompleteTextView etSchool;
	@Bean
	ToastComponent toast;
	private List<String> allUniveList;
	public static final String ENCODING = "UTF-8";
	public static final String OCCUPATION = "OCCUPATION";
	private List<OccupationBean> occupationBeanList;
	private OccupationAdapter occupationAdapter;
	ProfessionAdapter professionAdapter;
	private String profession;
	// private String[] data = { "学生", "IT/互联网/金融", "媒体/公关", "金融", "法律", "咨询",
	// "文化/艺术", "影视/娱乐", "教育/科研", "房地产/建筑", "医药/健康", "能源/环保", "政府机构" };
	private String data = "[[\"互联网/软件\",\"产品\",\"开发\",\"设计\",\"测试\",\"技术运维\",\"项目管理\",\"游戏策划\",\"运营\",\"编辑\",\"市场公关\",\"销售\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"数据\",\"其他\",\"全部\"],[\"电子/硬件\",\"项目管理\",\"生产\",\"仓储\",\"之间\",\"维修\",\"市场公关\",\"销售\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"电信\",\"维修\",\"市场公关\",\"销售\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"金融\",\"公募基金\",\"VC/PE\",\"信托\",\"资产管理\",\"保险\",\"金融租赁\",\"财务公司\",\"典当拍卖\",\"金融市场管理机构\",\"其他\",\"全部\"],[\"法律/咨询/会计\",\"审计税务\",\"商业咨询\",\"资产评估\",\"市场调查\",\"广告公关\",\"猎头服务\",\"其他人力资源服务\",\"租赁服务\",\"其他\",\"全部\"],[\"教育科研\",\"中小学教师\",\"培训老师\",\"教学管理\",\"课程开发\",\"招生顾问\",\"市场公关\",\"销售\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"文化传媒\",\"记者编辑\",\"策划\",\"设计\",\"摄影\",\"服务\",\"导演\",\"主持\",\"演员模特\",\"发行制片\",\"后期\",\"经纪人\",\"体育\",\"其他\",\"全部\"],[\"贸易\",\"仓储\",\"单证报关\",\"市场公关\",\"销售\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"零售\",\"店员导购\",\"安防仓储\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"酒店旅游\",\"景点管理\",\"导游\",\"厨师\",\"市场公关\",\"销售\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"生活服务\",\"美容美发\",\"家政\",\"维修\",\"市场公关\",\"销售\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"政府/社会组织\",\"事业单位\",\"公务员\",\"军人\",\"竣工\",\"社会团体及非营利组织\",\"其他\",\"全部\"],[\"轻工业制造\",\"家用电器\",\"食品饮料\",\"生活日用品\",\"纺织服装\",\"皮革箱包\",\"建材家居\",\"珠宝首饰\",\"烟草\",\"市场公关\",\"销售\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"重工业制造\",\"电气设备\",\"工业机械\",\"工程机械\",\"仪表仪器\",\"机械部件\",\"汽车\",\"化工\",\"铁路船舶航天设备\",\"采矿\",\"冶炼\",\"市场公关\",\"销售 财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"制药/生物科技\",\"研发实验\",\"生产\",\"供应链\",\"采购\",\"仓储\",\"质检\",\"注册\",\"市场公关\",\"销售\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"医疗\",\"营养师\",\"市场公关\",\"销售\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"房产建筑\",\"规划设计\",\"工程施工\",\"项目管理\",\"物业管理\",\"房地产中介\",\"市场公关\",\"销售\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"交通运输\",\"司机\",\"仓储\",\"货代\",\"调度\",\"快递\",\"船员”,”机长\",\"乘务\",\"地勤\",\"路政\",\"市场公关\",\"销售\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"能源环保水利\",\"市场公关\",\"销售\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"农林牧渔\",\"农林牧渔\",\"市场公关\",\"销售\",\"财务\",\"人力资源\",\"战略/投资\",\"法务\",\"行政/IT\",\"高管\",\"其他\",\"全部\"],[\"学校\"],[\"不限\"]]";

	@AfterViews
	public void init() {
		initLayout();
		initOccupation();
		parseJsonData();
		setAutoApdater();
	}

	private void initLayout() {
		// 窗口对齐屏幕宽度
		Window win = this.getWindow();
		win.getDecorView().setPadding(100, 100, 100, 100);
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		lp.gravity = Gravity.TOP;// 设置对话框置顶显示
		win.setAttributes(lp);
	}

	private void initOccupation() {
		occupationBeanList = new ArrayList<OccupationBean>();
		try {
			JSONArray array = new JSONArray(data);
			for (int i = 0; i < array.length(); i++) {
				JSONArray jsonArray = array.getJSONArray(i);
				OccupationBean bean = new OccupationBean();
				bean.occupationList = new ArrayList<String>();
				for (int j = 0; j < jsonArray.length(); j++) {
					if (j == 0) {
						bean.profession = jsonArray.getString(0);
						continue;
					}
					bean.occupationList.add(jsonArray.getString(j));
				}
				occupationBeanList.add(bean);
			}
		} catch (JSONException e) {
			toast.show("解析失败");
		}
		professionAdapter = new ProfessionAdapter(this, occupationBeanList);
		lvProfession.setAdapter(professionAdapter);
		OccupationBean occupationBean = professionAdapter.getItem(professionAdapter.getPositionPress());
		profession = occupationBean.profession;
		occupationAdapter = new OccupationAdapter(mContext, occupationBean.occupationList);
		lvOccupation.setAdapter(occupationAdapter);
		lvProfession.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == professionAdapter.getCount() - 1) {
					setResult("");
					return;
				}
				OccupationBean occupationBean = professionAdapter.getItem(position);
				professionAdapter.setPositionPress(position);
				profession = occupationBean.profession;
				occupationAdapter.updataListView(occupationBean.occupationList);
				if (position == professionAdapter.getCount() - 2) {
					lvOccupation.setVisibility(View.GONE);
					etSchool.setVisibility(View.VISIBLE);
				} else {
					lvOccupation.setVisibility(View.VISIBLE);
					etSchool.setVisibility(View.GONE);
				}
			}
		});
		lvOccupation.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String occupation = profession + " " + occupationAdapter.getItem(position);
				setResult(occupation);
			}

		});
	}

	private void setResult(String occupation) {
		Intent intent = new Intent();
		intent.putExtra(OCCUPATION, occupation);
		setResult(-1, intent);
		finish();
	}

	// json解析各个学校数据
	private void parseJsonData() {
		allUniveList = new ArrayList<String>();
		String jsonDat = getFromAssets("alluniveList.js");
		try {
			JSONArray ja = new JSONArray(jsonDat);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject object = ja.getJSONObject(i);
				JSONArray jsA = object.getJSONArray("univs");
				for (int j = 0; j < jsA.length(); j++) {
					JSONObject jO = jsA.getJSONObject(j);
					String data = jO.getString("name");
					allUniveList.add(data);
				}
			}
		} catch (JSONException e) {
			return;
		}
	}

	// 从assets 文件夹中获取文件并读取数据
	public String getFromAssets(String fileName) {
		String result = "";
		try {
			InputStream in = getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	UniversityAdapter adapter;

	/**
	 * 为AutoCompleteTextView设置Adpater
	 */
	private void setAutoApdater() {
		adapter = new UniversityAdapter(mContext, allUniveList);
		etSchool.setAdapter(adapter);
		etSchool.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String school = "学生 " + adapter.getItem(position);
				setResult(school);
			}
		});
	}

}
