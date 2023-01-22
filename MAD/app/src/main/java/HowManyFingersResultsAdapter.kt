import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mad.HowManyFingersDao
import com.example.mad.HowManyFingersResult
import com.example.mad.R
import kotlinx.android.synthetic.main.flower_list_item.view.*
import kotlinx.android.synthetic.main.how_many_fingers_results_list.view.*

class HowManyFingersResultsAdapter: BaseAdapter {

    lateinit var context: Context
    lateinit var howManyFingersResults: List<HowManyFingersResult>

    constructor (context: Context, howManyFingersResults: List<HowManyFingersResult>): super(){
        this.context = context
        this.howManyFingersResults = howManyFingersResults
    }

    override fun getCount(): Int {
        return howManyFingersResults.size
    }

    override fun getItem(position: Int): HowManyFingersResult {
        return howManyFingersResults[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = layoutInflater.inflate(R.layout.how_many_fingers_results_list, null, true)
        val userNumberTextView = itemView.user_number as TextView
        val correctNumberTextView = itemView.correct_number as TextView
        val resultTextView = itemView.result as TextView
        if(position % 2 == 0) itemView.setBackgroundColor((Color.GRAY))
        else itemView.setBackgroundColor((Color.WHITE))
        userNumberTextView.text = getItem(position).userNumber
        correctNumberTextView.text = getItem(position).correctNumber
        resultTextView.text = getItem(position).result
        return itemView
    }

}