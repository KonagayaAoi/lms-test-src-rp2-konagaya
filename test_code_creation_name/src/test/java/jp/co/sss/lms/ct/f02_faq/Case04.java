package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
		goTo("http://localhost:8080/lms");
		assertEquals("ログイン | LMS", webDriver.getTitle(), "画面タイトルが一致しません");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		WebElement loginIdField = webDriver.findElement(By.id("loginId"));
		WebElement passwordField = webDriver.findElement(By.id("password"));
		WebElement loginButton = webDriver.findElement(By.cssSelector("input[value='ログイン']"));

		loginIdField.sendKeys("StudentAA01");
		passwordField.sendKeys("StudentBB01");
		loginButton.click();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals("コース詳細 | LMS", webDriver.getTitle(), "コース詳細画面に遷移していません");

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 1. まずは上部メニューの機能プルダウンをクリックして開く
		WebElement dropdown = webDriver.findElement(By.linkText("機能"));
		dropdown.click();

		// プルダウンが開く前に次の処理に行くと失敗するため少し待機
		try {
			Thread.sleep(500); // 0.5秒待機
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 2. 開いたメニューの中にある「ヘルプ」リンクをクリックする
		WebElement helpLink = webDriver.findElement(By.linkText("ヘルプ"));
		helpLink.click();

		// 画面遷移の待機
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 期待値の検証：ヘルプ画面に遷移していること
		assertEquals("ヘルプ | LMS", webDriver.getTitle(), "ヘルプ画面に遷移していません");

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 1. クリックする前の「現在のタブ」のIDを記憶しておく
		String originalWindow = webDriver.getWindowHandle();

		WebElement faqLink = webDriver.findElement(By.linkText("よくある質問"));
		faqLink.click(); // ここで別タブが開く

		// 画面遷移の待機
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 2. 開いているすべてのタブを取得し、新しく開いたタブに切り替える
		for (String windowHandle : webDriver.getWindowHandles()) {
			if (!originalWindow.equals(windowHandle)) {
				webDriver.switchTo().window(windowHandle);
				break;
			}
		}

		// 新しいタブでの期待値の検証：よくある質問画面に遷移していること
		assertEquals("よくある質問 | LMS", webDriver.getTitle(), "よくある質問画面に遷移していません");
		getEvidence(new Object() {
		});
	}

}
