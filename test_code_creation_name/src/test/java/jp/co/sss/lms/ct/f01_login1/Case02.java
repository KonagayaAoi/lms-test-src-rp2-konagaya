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
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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

		// 2. 画面タイトルの確認
		assertEquals("ログイン | LMS", webDriver.getTitle(), "画面タイトルが一致しません");

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		// 1. トップページへアクセス
		goTo("http://localhost:8080/lms");

		// 2. 要素の取得と入力
		WebElement loginIdField = webDriver.findElement(By.id("loginId"));
		WebElement passwordField = webDriver.findElement(By.id("password"));
		WebElement loginButton = webDriver.findElement(By.cssSelector("input[value='ログイン']"));

		loginIdField.sendKeys("dummy_user");
		passwordField.sendKeys("dummy_pass");

		// 3. ログインボタン押下
		loginButton.click();

		// 4. 期待値の検証：画面遷移していないこと（タイトルがログイン画面のままであること）
		assertEquals("ログイン | LMS", webDriver.getTitle(), "ログイン画面から遷移してしまっています");

		// 5. 期待値の検証：エラーメッセージの表示と内容の確認
		WebElement errorMessage = webDriver.findElement(By.cssSelector("span.help-inline.error"));
		assertTrue(errorMessage.isDisplayed(), "エラーメッセージが表示されていません");

		// 期待されるエラーメッセージの文言
		String expectedErrorText = "ログインに失敗しました。";

		// 完全一致ではなく、部分一致を使用
		assertTrue(errorMessage.getText().contains(expectedErrorText),
				"想定したエラーメッセージと異なります。実際のエラー: " + errorMessage.getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 ログインIDのみ空欄でログイン")
	void test03() {
		goTo("http://localhost:8080/lms");

		WebElement loginIdField = webDriver.findElement(By.id("loginId"));
		WebElement passwordField = webDriver.findElement(By.id("password"));
		WebElement loginButton = webDriver.findElement(By.cssSelector("input[value='ログイン']"));

		loginIdField.sendKeys(""); // 空欄
		passwordField.sendKeys("dummy_pass");
		loginButton.click();

		// ページ内のすべてのエラーメッセージ要素を取得し、対象の文言が含まれているか検証
		String pageText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(pageText.contains("ログインIDは必須です。"), "ログインIDの必須エラーメッセージが表示されていません");

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 パスワードのみ空欄でログイン")
	void test04() {
		goTo("http://localhost:8080/lms");

		WebElement loginIdField = webDriver.findElement(By.id("loginId"));
		WebElement passwordField = webDriver.findElement(By.id("password"));
		WebElement loginButton = webDriver.findElement(By.cssSelector("input[value='ログイン']"));

		loginIdField.sendKeys("dummy_user");
		passwordField.sendKeys(""); // 空欄
		loginButton.click();

		// ページ内のすべてのエラーメッセージ要素を取得し、対象の文言が含まれているか検証
		String pageText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(pageText.contains("パスワードは必須です。"), "パスワードの必須エラーメッセージが表示されていません");

		getEvidence(new Object() {
		});
	}

}
