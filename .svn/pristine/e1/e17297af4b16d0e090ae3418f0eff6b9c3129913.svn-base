package cn.sx.decentworld.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.ConstantIntent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_templet)
public class TempletActivity extends BaseFragmentActivity implements OnClickListener {
	@ViewById(R.id.tv_back)
	TextView tvBack;
	@ViewById(R.id.tv_ensure)
	TextView tvEnsure;
	@ViewById(R.id.tv_introduce_detail)
	TextView tvIntroduceDetail;
	private int careerPeriod;
	private String wan = "个人履历\n1944年至1947年任胶东区招远县齐山区中队文书，招远县独立营通信员、班长，胶东军区抗大 分校教导二团学习，胶东军区五旅十三团三营八连文书。\n1992年10月在中共第十四次全国代表大会上当选为中国共产党中央军事委员会委员。\n1993年3月在全国人大第八届一次会议上被任命为中华人民共和国中央军事委员会委员，国务委员兼国防部部长。\n1995年9月在中共第十四届五中全会上被增补为中央军委副主席。\n1998年3月至2003年3月任中华人民共和国中央军事委员会副主席，国务委员兼国防部部长。\n担任职务\n中共第十二届、十三届、十四届、十五届中央委员，第十五届中央政治局委员。\n获得荣誉\n1952年荣立一等功。\n1985获三级解放勋章。\n1988年9月被授予上将军衔。";
	private String careerSuccess = "核心能力\n1、大数据和数据质量研究\n2、博士研究方向是下一代数据库——包括XML数据库和具有数据质量管理功能的数据库。目前的研究兴趣是用于大数据处理和分析的大规模分布式内存计算\n3、众包数据采集模式\n4、众包数据采集中的采集动力、采集质量管理、采集绩效管理\n5、大数据上的信用分析\n6、基于大数据金融风控模型建立和运营\n我的成就\n回国前，贾西贝博士于2008年获爱丁堡皇家学会及苏格兰企业局资助在斯特莱斯克莱德大学商学院企业家研究中心，剑桥大学Judge商学院和麻省理工学院企业家中心参加科研成果商业化培训，是英国重点培养的科研成果商业化人才，当时整个英国获得如此待遇的，只有8个人。是世界上首个自动化、智能化的第三代数据质量管理系统的开发者。牵头和参与过多项世界级数据质量管理、半结构化数据管理领域的项目，包括爱丁堡皇家学会、苏格兰企业局、英国EPSRC（相当于与英国的自然科学基金委员会）、IBM等机构资助的项目。\n2010年回国后，创立华傲数据技术有限公司，主要从事大数据管理、数据质量管理及数据集成、分析等领域，提供面向金融、电信、零售、政府、互联网等行业的基础软件产品、服务和整体解决方案。先后兼任哈尔滨工程大学、黑龙江大学兼职教授。2012年3月被聘为北京市特聘专家。2012中国留学人员创业园百家最具潜力创业企业。2013年《哈佛商业评论》引领大数据发展的企业占位图3家上榜中国企业之一，数据清理领域唯一的上榜中国企业。\n除此之外，贾西贝还在国际顶级和一流数据库会议及学刊发表10余篇论文，已申请PCT国际专利31项和国内发明专利83项，登记软件著作权7项。美国专利5项，英国专利5项。2007年获IEEE国际数据工程大会（ICDE）最佳论文奖，其中多项技术已经应用于知名跨国公司。";
	private String careerIsToSuccess = "毕业于清华大学材料科学与工程学士（1999），范德比尔特大学电子工程博士（2003），美国耶鲁大学商学院MBA\n曾任职同创伟业创投等投资机构，目前担任君联资本投资副总裁，从事高科技投资工作，具有丰富的初创企业考察、筛选及投资经验。专注TMT和创业消费、在在线教育、体育、智能硬件、先进制造业和互联网+传统行业等领域主导投资10家左右公司。\n在进入风险投资行业执勤啊，从事电子和半导体领域的研发工作。我从事投资行业多年，对于企业的融资运作有非常丰富的经验，曾主导操作多个项目投融资，设计在线教育、体育、电商、智能硬件、先进制造业等多个领域。我能够从一个投资人的角度为你提供相应的建议和解决方案，包括:\n1、如何快速了解投资机构和投资人；\n2、如何撰写商业计划书；融资过程种的关键环节。\n企业股权投资、项目考察、估值。\n第一次见风险投资机构应该准备些什么\n封信啊投资看项目的关注点\n投资人需要什么样的商业计划书内容。";
	private String careerStart = "我是【姓名】，我现在【某某公司】担任【职务】。我毕业于【毕业院校】。我喜欢【爱好】，是一个【性格】。我有【N】年【某某领域】从业经验，期间【具体做了哪些项目或工作】，【得到了那些经验/收获】。【想与学院分享的心得】，希望我的经验能够帮到你。";

	@AfterViews
	public void init() {
		TGetIntent();
		tvBack.setOnClickListener(this);
		tvEnsure.setOnClickListener(this);
		initTemplet();
	}

	public void TGetIntent() {
		careerPeriod = getIntent().getIntExtra(ConstantIntent.ACTIVITY_ACHIEVEMENT_CAREER_PERIOD, -1);
	}

	private void initTemplet() {
		switch (careerPeriod) {
		case ConstantIntent.ACTIVITY_ACHIEVEMENT_CAREER_START:
			tvIntroduceDetail.setText(careerStart);
			break;
		case ConstantIntent.ACTIVITY_ACHIEVEMENT_CAREER_ISTO_SUCCESS:
			tvIntroduceDetail.setText(careerIsToSuccess);
			break;
		case ConstantIntent.ACTIVITY_ACHIEVEMENT_CAREER_SUCCESS:
			tvIntroduceDetail.setText(careerSuccess);
			break;
		case ConstantIntent.ACTIVITY_ACHIEVEMENT_WAN:
			tvIntroduceDetail.setText(wan);
			break;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_back:

			break;

		case R.id.tv_ensure:
			Intent intent = new Intent();
			intent.putExtra(ConstantIntent.ACTIVITY_ACHIEVEMENT_INTRODUCE_DETAIL, tvIntroduceDetail.getText().toString());
			setResult(RESULT_OK, intent);
			break;
		}
		finish();
	}
}
