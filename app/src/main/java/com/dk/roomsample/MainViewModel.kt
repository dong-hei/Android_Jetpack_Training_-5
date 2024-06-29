package com.dk.roomsample

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk.roomsample.db.TextDatabase
import com.dk.roomsample.entity.TextEntity
import com.dk.roomsample.entity.WordEntity
import com.dk.roomsample.repo.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * db에 컨텍스트를 넣어야 하기때문에 AndroidViewModel로 받는다.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    val context = getApplication<Application>().applicationContext
    val db = TextDatabase.getDatabase(context)

    private var _textList = MutableLiveData<List<TextEntity>>()
    val textList : LiveData<List<TextEntity>>
        get() = _textList

    private var _wordList = MutableLiveData<List<WordEntity>>()
    val wordList : LiveData<List<WordEntity>>
        get() = _wordList

    val repo = Repository(context)

    fun getData() = viewModelScope.launch(Dispatchers.IO) {
        Log.d("MainViewModel", db.textDao().getAllData().toString())
        Log.d("MainViewModel", db.wordDao().getAllData().toString())
        _textList.postValue(repo.getTextList())
        _wordList.postValue(repo.getWordList())
    }

    fun insertData(text : String) = viewModelScope.launch(Dispatchers.IO) {
//        db.textDao().insert(TextEntity(0, text))
//        db.wordDao().insert(WordEntity(0, text))
        repo.insertTextData(text)
        repo.insertWordData(text)
    }

    fun deleteData() = viewModelScope.launch(Dispatchers.IO) {
//        db.textDao().deleteAllData()
//        db.wordDao().deleteAllData()
        repo.deleteTextData()
        repo.deleteWordData()
    }
}