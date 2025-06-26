package com.Main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.AccounbtSettings.AccountSettings;
import com.Configutaion.Configuration;
import com.CreateEstimate.CreateEstimate;
import com.Dashboard.CreatedDateFilter;
import com.Dashboard.Dashbaord;
import com.Dashboard.EstimatorFilter;
import com.Dashboard.Search;
import com.Dashboard.SidebarToggle;
import com.Dashboard.StatusFilter;
import com.Layout.Inspection;
import com.Layout.Introduction;
import com.Layout.LayoutCreation;
import com.Layout.LayoutPage;
import com.Layout.QuoteDetailsPage;
import com.Layout.Title;
import com.login.Login;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MainRes {

	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://smarterlead-001-site9.rtempurl.com/");
		driver.manage().window().maximize();
		System.out.println("Browser launched");

		// Login functionality
		Login login = new Login();
		login.performLogin(driver);

		// Estimate creation
		// CreateEstimate estimatecreate = new CreateEstimate();
		// estimatecreate.EstimateCreation(driver);

		// Dashboard functionalities
		//Dashbaord dashboard = new Dashbaord(driver);
		//dashboard.selectDarkTheme();
		//dashboard.selectSystemTheme();
		//dashboard.selectLightTheme();

		// call the class method related to the dahsboard one by one for execution
		//SidebarToggle sidebar = new SidebarToggle(driver);
		//sidebar.executeSidebarToggleFlow();

		
		//Search search = new Search(driver);
		//search.performSearch();
		
		//EstimatorFilter estimatorFilter = new EstimatorFilter(driver);
		//estimatorFilter.applyMultipleEstimators();
		
		//StatusFilter statusFilter = new StatusFilter(driver);
		//statusFilter.applyAllStatuses();
		
		//CreatedDateFilter createdDateFilter = new CreatedDateFilter(driver);
		//createdDateFilter.applyAllDateFilters();

		// Configuration functionality
		Configuration configuration = new Configuration(driver);
		configuration.setupConfiguration();

		// Navigate to account settings
		AccountSettings accountSettings = new AccountSettings(driver);
		accountSettings.navigateToAccountSettings();

		// Account settings option call
		LayoutCreation layoutMenu = new LayoutCreation(driver);
		layoutMenu.createLayout();
		
		// Title page
		Title titlePage = new Title(driver);
		titlePage.TitlePage();
		
		// Introduction page
		Introduction introductionPage = new Introduction(driver);
		introductionPage.Intro();
		
		// Inspection page
		Inspection inspection =  new Inspection(driver);
		inspection.performInspection();
		
		// Layout page
		LayoutPage layoutMap = new LayoutPage(driver);
		layoutMap.navigateToLayoutPage();
		
		// Quote details page
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage(driver);
		quoteDetailsPage.fillQuoteDetails();
	}
}
