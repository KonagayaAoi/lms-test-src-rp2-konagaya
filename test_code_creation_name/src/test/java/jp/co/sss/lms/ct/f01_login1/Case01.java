package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能①
 * ケース01
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース01 ログイン画面への遷移")
public class Case01 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// 1. トップページにアクセス
		goTo("http://localhost:8080/lms");

		// 2. 期待値の検証：画面タイトルの確認
		assertEquals("ログイン | LMS", webDriver.getTitle(), "画面タイトルが一致しません");

		// 3. 期待値の検証：入力項目の存在と表示確認
		WebElement loginIdField = webDriver.findElement(By.id("loginId"));
		WebElement passwordField = webDriver.findElement(By.id("password"));

		assertTrue(loginIdField.isDisplayed(), "ログインID入力欄が表示されていません");
		assertTrue(passwordField.isDisplayed(), "パスワード入力欄が表示されていません");

		// パスワード入力欄の属性が password（伏せ字）形式であることの検証
		assertEquals("password", passwordField.getAttribute("type"), "パスワード入力欄が伏せ字形式になっていません");

		// 4. 期待値の検証：ボタン・リンクの確認
		WebElement loginButton = webDriver.findElement(By.cssSelector("input[value='ログイン']"));
		WebElement forgotPasswordLink = webDriver.findElement(By.linkText("パスワードを忘れた方はこちら"));

		assertTrue(loginButton.isDisplayed(), "ログインボタンが表示されていません");
		assertTrue(forgotPasswordLink.isDisplayed(), "「パスワードを忘れた方はこちら」リンクが表示されていません");

		// 5. 期待値の検証：画面下部パネル（お知らせ・マニュアル・FAQ）の確認
		// それぞれのパネルの見出しテキストが存在し、正しく表示されているか検証
		WebElement infoPanelTitle = webDriver.findElement(By.xpath("//h3[contains(text(), 'お知らせ')]"));
		WebElement manualPanelTitle = webDriver.findElement(By.xpath("//h3[contains(text(), 'マニュアルはこちらからダウンロード')]"));
		WebElement faqPanelTitle = webDriver.findElement(By.xpath("//h3[contains(text(), 'よくあるご質問はこちら')]"));

		assertTrue(infoPanelTitle.isDisplayed(), "「お知らせ」パネルが表示されていません");
		assertTrue(manualPanelTitle.isDisplayed(), "「マニュアルはこちらからダウンロード」パネルが表示されていません");
		assertTrue(faqPanelTitle.isDisplayed(), "「よくあるご質問はこちら」パネルが表示されていません");

		// 6. エビデンスの取得
		getEvidence(new Object() {
		});
	}
}
