package com.example.tasklisttaker.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import com.example.tasklisttaker.ui.main.TaskNoteState

class TaskNoteView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

   private var text =""

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        this.text =text.toString()
    }

    fun setState(state: TaskNoteState) {
        paint.style = Paint.Style.FILL

        when (state) {
            TaskNoteState.COMPLETE -> {
                paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                setTextColor(Color.GRAY)
                Log.d("TAG", "setState: $text COMPLETE called")
                postInvalidate()
            }
            TaskNoteState.INCOMPLETE -> {
                paint.flags = 0
                setTextColor(Color.BLACK)
                Log.d("TAG", "setState: $text INCOMPLETE called")
            }

        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // if u want to do anything

    }


}