package com.dk.roomsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dk.roomsample.adapter.CustomAdapter
import com.dk.roomsample.entity.TextEntity
import com.dk.roomsample.db.TextDatabase
import com.dk.roomsample.entity.WordEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProvider(this).get(MainViewModel::class.java)
        vm.getData()

        val db = TextDatabase.getDatabase(this)
        val inputArea = findViewById<EditText>(R.id.textInputArea)
        val insertBtn = findViewById<Button>(R.id.insert)
        val getAllBtn = findViewById<Button>(R.id.getData)
        val deleteBtn = findViewById<Button>(R.id.delete)

        /** Dispatchers = 스레드를 배치해 관리하는 역할
         *             .Main 기본 Android 스레드에서 코루틴 실행
         *             (suspend함수를 호출하고 Android UI 프레임워크 작업을 실행, LiveData 객체를 업뎃)
         *
         *             .IO 기본 스레드 외부에서 디스크 또는 네트워크 I/O를 실행하도록 최적화
         *             (Room 용도로 주로 사용)
         *
         *             .Default CPU를 많이 사용하는 작업을 기본 스레드 외부에서 실행하도록 최적화
         *             (목록을 정렬하고 JSON 파싱)
         */

        insertBtn.setOnClickListener {

            vm.insertData(inputArea.text.toString())
            inputArea.setText("")


//            vm으로 대체
//            CoroutineScope(Dispatchers.IO).launch {
//                db.textDao().insert(TextEntity(0, inputArea.text.toString()))
//                db.wordDao().insert(WordEntity(0, inputArea.text.toString()))
//                Log.d("db search", db.textDao().getAllData().toString())
//                db.textDao().getAllData()
//
//            }
        }

        val rv = findViewById<RecyclerView>(R.id.rv)
        vm.textList.observe(this, Observer {
            val customAdapter = CustomAdapter(it)
            rv.adapter = customAdapter
            rv.layoutManager = LinearLayoutManager(this)
        })
        getAllBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                vm.getData()

//                vm으로 대체
//                Log.d("text, get All", db.textDao().getAllData().toString())
//                Log.d("word, get All", db.textDao().getAllData().toString())

            }
        }

        deleteBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                vm.deleteData()

//                vm으로 대체
//                db.textDao().deleteAllData()
//                db.wordDao().deleteAllData()
            }
        }
    }
}