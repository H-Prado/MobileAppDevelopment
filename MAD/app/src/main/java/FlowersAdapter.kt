import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mad.R
import kotlinx.android.synthetic.main.flower_list_item.view.*

class FlowersAdapter: BaseAdapter {

    lateinit var context: Context
    lateinit var flowerNames: ArrayList<String>
    lateinit var flowerImages: ArrayList<Int>

    constructor (context: Context, flowerNames: ArrayList<String>, flowerImages: ArrayList<Int>): super(){
        this.context = context
        this.flowerNames = flowerNames
        this.flowerImages = flowerImages
    }

    override fun getCount(): Int {
        return flowerNames.size
    }

    override fun getItem(position: Int): Any {
        return flowerNames[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = layoutInflater.inflate(R.layout.flower_list_item, null, true)
        val flowerNameTextView = itemView.flower_name as TextView
        val flowerImageView = itemView.flower_image as ImageView
        if(position % 2 == 0) flowerNameTextView.setBackgroundColor((Color.WHITE))
        flowerNameTextView.text = getItem(position).toString()
        flowerImageView.setImageResource(flowerImages[position])
        return itemView
    }

}