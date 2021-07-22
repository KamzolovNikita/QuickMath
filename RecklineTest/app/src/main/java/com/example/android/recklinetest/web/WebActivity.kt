package com.example.android.recklinetest.web

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.android.recklinetest.databinding.ActivityWebBinding


class WebActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebBinding.inflate(layoutInflater, null, false)



        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress < 100 && binding.progressBarWithShadowing.visibility == ProgressBar.GONE) {
                    binding.progressBarWithShadowing.visibility = ProgressBar.VISIBLE
                }
                if (progress == 100) {
                    binding.progressBarWithShadowing.visibility = ProgressBar.GONE
                }
            }
        }

        binding.webView.loadUrl("https://www.google.com")
        binding.webView.webViewClient = WebViewClient()

        setContentView(binding.root)
    }

    override fun onBackPressed() {
        if(binding.webView.canGoBack()) {
            binding.webView.goBack()
        }
        else {
            super.onBackPressed()
        }
    }
}