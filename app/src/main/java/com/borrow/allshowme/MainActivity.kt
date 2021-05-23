package com.borrow.allshowme

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_web_view.*


private const val NUM_PAGES = 5

class MainActivity : FragmentActivity() {
    private lateinit var mPager: ViewPager
    private var keyWord : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //tabLayout과 ViewPager 연동
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addTab(tabLayout.newTab().setText("네이버"));
        tabLayout.addTab(tabLayout.newTab().setText("쿠팡"));
        tabLayout.addTab(tabLayout.newTab().setText("티몬"));
        tabLayout.addTab(tabLayout.newTab().setText("G마켓"));
        tabLayout.addTab(tabLayout.newTab().setText("옥션"));

        // ViewPager 움직임 설정
        mPager = viewPager
        mPager.setPageTransformer(true, ZoomOutPageTransformer())
        // ViewPager Adapter 설정
        val adapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager.adapter = adapter


        searchView.setOnClickListener {
            searchView.isIconified = false
        }

        //검색완료 시 키워드 저장 후 브라우저 호출하기
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                keyWord = query
                (viewPager.adapter as ScreenSlidePagerAdapter).notifyDataSetChanged()
                return false
            }
        })

        //TODO 공유하기 클릭시 현재 사이트 주소 알아내기
        share_btn.setOnClickListener {
            /*val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/html"
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "haha")
            startActivity(Intent.createChooser(sharingIntent, "Share using text"))*/
        }

    }

    //TODO 뒤로가기 버튼시 웹뷰만 뒤로가게하는거 구현
    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }else{
            if (mPager.currentItem == 0) {
                // If the user is currently looking at the first step, allow the system to handle the
                // Back button. This calls finish() on this activity and pops the back stack.
                super.onBackPressed()
            } else {
                // Otherwise, select the previous step.
                mPager.currentItem = mPager.currentItem - 1
            }
        }
    }


    //ViewPager Adapter
    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int = NUM_PAGES

        override fun getPageTitle(position: Int): CharSequence? {
            when(position){
                0 -> return "네이버"
                1 -> return "쿠팡"
                2 -> return "티몬"
                3 -> return "G마켓"
                4 -> return "옥션"
            }
            return ""
        }
        override fun getItem(position: Int): Fragment {
            val fragment = WebViewFragment()
            val bundle =Bundle(1)
            when(position){
                0 -> bundle.putString(
                    "url",
                    "https://msearch.shopping.naver.com/search/all?query=" + keyWord
                )
                1 -> bundle.putString("url", " https://m.coupang.com/nm/search?q=" + keyWord)
                2 -> bundle.putString(
                    "url",
                    " http://m.search.tmon.co.kr/search?#keyword=" + keyWord
                )
                3 -> bundle.putString(
                    "url",
                    " http://browse.gmarket.co.kr/search?keyword=" + keyWord
                )
                4 -> bundle.putString(
                    "url",
                    " https://browse.auction.co.kr/search?keyword=" + keyWord
                )
            }
            fragment.arguments = bundle
            return fragment
        }

        //ViewPager 새로고침 구현
        override fun getItemPosition(`object`: Any): Int {
            return POSITION_NONE
        }
    }



}