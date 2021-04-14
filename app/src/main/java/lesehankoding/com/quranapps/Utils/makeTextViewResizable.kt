package lesehankoding.com.quranapps.Utils

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import android.widget.TextView.BufferType


fun makeTextViewResizable(
    tv: TextView,
    maxLine: Int, expandText: String,
    clicked: () -> Unit
//    viewMore: Boolean
) {
    if (tv.tag == null) {
        tv.tag = tv.text
    }
    val vto = tv.viewTreeObserver
    vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            val obs = tv.viewTreeObserver
            obs.removeGlobalOnLayoutListener(this)
//            if (maxLine == 0) {
//                val lineEndIndex = tv.layout.getLineEnd(0)
//                val text = tv.text.subSequence(
//                    0,
//                    lineEndIndex - expandText.length + 1
//                )
//                    .toString() + "... " + expandText
//                tv.text = text
//                tv.movementMethod = LinkMovementMethod.getInstance()
//                tv.setText(
//                    addClickablePartTextViewResizable(
//                        tv.text
//                            .toString(), tv, maxLine, expandText,
//                        clicked = {
//                            clicked.invoke()
//                        }
//                    ), BufferType.SPANNABLE
//                )
//            } else
                if (maxLine > 0 && tv.lineCount >= maxLine) {
                    val lineEndIndex = tv.layout.getLineEnd(maxLine - 1)
                    val text = tv.text.subSequence(
                        0,
                        lineEndIndex - expandText.length + 1
                    )
                        .toString() + "... " + expandText
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                        addClickablePartTextViewResizable(
                            tv.text
                                .toString(), tv, expandText,
                            clicked = {
                                clicked.invoke()
                            }
                        ), BufferType.SPANNABLE
                    )
                }
//                else {
//                val lineEndIndex = tv.layout.getLineEnd(
//                    tv.layout.lineCount - 1
//                )
//                val text = tv.text.subSequence(0, lineEndIndex)
//                    .toString() + "... " + expandText
//                tv.text = text
//                tv.movementMethod = LinkMovementMethod.getInstance()
//                tv.setText(
//                    addClickablePartTextViewResizable(
//                        tv.text
//                            .toString(), tv, lineEndIndex, expandText,
//                        clicked = {
//                            clicked.invoke()
//                        }
//                    ), BufferType.SPANNABLE
//                )
//            }
        }
    })
}

private fun addClickablePartTextViewResizable(
    strSpanned: String, tv: TextView,
    spanableText: String,
    clicked : () -> Unit
): SpannableStringBuilder? {
    val ssb = SpannableStringBuilder(strSpanned)
    if (strSpanned.contains(spanableText)) {
        ssb.setSpan(
            object : ClickableSpan() {
                override fun onClick(p0: View) {
//                    if (viewMore) {
//                        tv.layoutParams = tv.layoutParams
//                        tv.setText(
//                            tv.tag.toString(),
//                            BufferType.SPANNABLE
//                        )
//                        tv.invalidate()
//                        makeTextViewResizable(
//                            tv, maxLine, "lebih sedikit",
//                            false
//                        )
////                        tv.setTextColor(Color.BLACK)
//                    } else {

//                        tv.layoutParams = tv.layoutParams
//                        tv.setText(
//                            tv.tag.toString(),
//                            BufferType.SPANNABLE
//                        )
                        tv.invalidate()
//                        makeTextViewResizable(
//                            tv, maxLine, "selengkapnya",
//                            clicked = {
                        clicked.invoke()
//                            }
//                        )
//                        tv.setTextColor(Color.BLACK)
//                    }
                }
            }, strSpanned.indexOf(spanableText),
            strSpanned.indexOf(spanableText) + spanableText.length, 0
        )
    }
    return ssb
}