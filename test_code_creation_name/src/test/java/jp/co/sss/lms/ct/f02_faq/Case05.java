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
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
		WebElement dropdown = webDriver.findElement(By.linkText("機能"));
		dropdown.click();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement helpLink = webDriver.findElement(By.linkText("ヘルプ"));
		helpLink.click();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals("ヘルプ | LMS", webDriver.getTitle(), "ヘルプ画面に遷移していません");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		String originalWindow = webDriver.getWindowHandle();
		WebElement faqLink = webDriver.findElement(By.linkText("よくある質問"));
		faqLink.click();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (String windowHandle : webDriver.getWindowHandles()) {
			if (!originalWindow.equals(windowHandle)) {
				webDriver.switchTo().window(windowHandle);
				break;
			}
		}

		assertEquals("よくある質問 | LMS", webDriver.getTitle(), "よくある質問画面に遷移していません");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// 1. 検索キーワード入力欄の要素の取得と入力
		WebElement searchInput = webDriver.findElement(By.id("form"));
		searchInput.sendKeys("キャンセル");

		// 2. 検索ボタンの要素を取得
		WebElement searchButton = webDriver.findElement(By.cssSelector("input[value='検索']"));
		searchButton.click();

		// 検索結果の画面遷移（自画面）待機
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 期待値の検証：画面に「キャンセル」という文字が含まれていること
		String pageText = webDriver.findElement(By.tagName("body")).getText();
		assertTrue(pageText.contains("キャンセル"), "検索結果に指定したキーワードが含まれていません");

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// 1. クリアボタンを取得してクリック
		WebElement clearButton = webDriver.findElement(By.cssSelector("input[value='クリア']"));
		clearButton.click();

		// クリア処理待機
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 期待値の検証：検索キーワード入力欄が空欄（""）になっていること
		WebElement searchInput = webDriver.findElement(By.id("form"));
		assertEquals("", searchInput.getAttribute("value"), "クリアボタン押下後もキーワードが残っています");

		getEvidence(new Object() {
		});
	}

}
