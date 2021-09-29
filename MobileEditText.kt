package com.sdcz.androidtest.weidget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.*
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.sdcz.androidtest.R


/**
 * Author: Administrator
 * CreateDate: 2021/9/28 15:16
 * Description: @
 * @JvmOverloads注解的作用就是：在有默认参数值的方法中使用@JvmOverloads注解，则Kotlin就会暴露多个重载方法。
 */
class MobileEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.editTextStyle) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var draw: Drawable? = null
    private var drawShow: Drawable? = null

    init {
        draw = ContextCompat.getDrawable(context, R.drawable.icon_clear)
        val minimumHeight: Int = draw!!.minimumHeight
        val minimumWidth: Int = draw!!.minimumWidth
        //drawable将在被绘制在canvas的哪个矩形区域内。
        draw?.setBounds(0, 0, minimumWidth, minimumHeight)
        addTextChangedListener(TextWatcherEditText())
        //只能输入数字
        inputType = InputType.TYPE_CLASS_NUMBER
        //最大长度
        filters = arrayOf<InputFilter>(LengthFilter(13))
        isSingleLine = true
    }

    private fun isShow(isShow: Boolean) {
        drawShow = if (isShow) {
            draw
        } else {
            null
        }
        //在EditText某个位置设置图片
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], drawShow, compoundDrawables[3])
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN) {
            val isDelect = event.x > (width - totalPaddingRight) && event.x < (width - paddingRight) && event.y > 0 && event.y < height
            if (isDelect) {
                setText("")
            }
        }
        return super.onTouchEvent(event)
    }

    private inner class TextWatcherEditText() : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (TextUtils.isEmpty(text.toString())) {
                isShow(false)
            } else {
                isShow(true)
            }
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            var textContent = s.toString()
            var textLength = textContent.length
            if (textLength == 4 || textLength == 9) {

                if (textContent.substring(textLength - 1) == " "){
                    textContent = textContent.substring(0, length() - 1)
                    setText(textContent)
                    setSelection(textContent.length)
                }else{
                    textContent = textContent.substring(0, length() - 1) + " " + textContent.substring(textLength - 1)
                    setText(textContent)
                    setSelection(textContent.length)
                }

            }
        }

        override fun afterTextChanged(s: Editable?) {
        }

    }


}