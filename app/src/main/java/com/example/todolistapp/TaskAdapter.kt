package com.example.todolistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class TaskAdapter(private val taskList: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // ViewHolder cho mỗi task item
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskNameTextView: TextView = itemView.findViewById(R.id.taskNameTextView)
        val dueDateTextView: TextView = itemView.findViewById(R.id.dueDateTextView)
    }

    // Tạo ViewHolder mới
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    // Thiết lập dữ liệu cho ViewHolder
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = taskList[position]

        // Đặt tên nhiệm vụ
        holder.taskNameTextView.text = currentTask.name

        // Format ngày đến hạn và hiển thị
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(currentTask.dueDate)
        holder.dueDateTextView.text = "Đến hạn: $formattedDate"
    }

    // Trả về số lượng task trong danh sách
    override fun getItemCount(): Int {
        return taskList.size
    }
}