package com.example.todolistapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar
import java.util.Date


class MainActivity : AppCompatActivity() {

    private lateinit var taskEditText: EditText
    private lateinit var addButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter

    private val tasks = mutableListOf<Task>()
    private var selectedDate: Date = Calendar.getInstance().time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Đảm bảo layout đúng

        // Thêm try-catch để xác định vị trí lỗi
        try {
            // Khởi tạo các thành phần UI
            taskEditText = findViewById(R.id.taskEditText)
            addButton = findViewById(R.id.addButton)
            recyclerView = findViewById(R.id.recyclerView)

            // Thiết lập RecyclerView
            adapter = TaskAdapter(tasks)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter

            // Thiết lập sự kiện click cho nút Add
            addButton.setOnClickListener {
                addTask()
            }

            // Thiết lập sự kiện click cho nút chọn ngày
            findViewById<Button>(R.id.datePickerButton).setOnClickListener {
                showDatePicker()
            }
        } catch (e: Exception) {
            // Log lỗi
            e.printStackTrace()
            Toast.makeText(this, "Lỗi khởi tạo: ${e.message}", Toast.LENGTH_LONG).show()
        }

        findViewById<Button>(R.id.sortByNameButton).setOnClickListener {
            sortTasksByName()
        }

        findViewById<Button>(R.id.sortByDateButton).setOnClickListener {
            sortTasksByDate()
        }
    }

    // Tạo menu tùy chọn
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Xử lý sự kiện chọn menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_name -> {
                sortTasksByName()
                true
            }
            R.id.sort_by_date -> {
                sortTasksByDate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Thêm nhiệm vụ mới
    private fun addTask() {
        val taskName = taskEditText.text.toString().trim()

        if (taskName.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên nhiệm vụ", Toast.LENGTH_SHORT).show()
            return
        }

        // Tạo task mới và thêm vào danh sách
        val newTask = Task(taskName, selectedDate)
        tasks.add(newTask)

        // Cập nhật RecyclerView
        adapter.notifyItemInserted(tasks.size - 1)

        // Xóa nội dung EditText sau khi thêm
        taskEditText.text.clear()

        // Đặt lại ngày đã chọn về ngày hiện tại
        selectedDate = Calendar.getInstance().time
        updateDateButtonText()

        Toast.makeText(this, "Đã thêm nhiệm vụ", Toast.LENGTH_SHORT).show()
    }

    // Hiển thị DatePicker để chọn ngày
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        calendar.time = selectedDate

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                selectedDate = calendar.time
                updateDateButtonText()
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    // Cập nhật nội dung hiển thị trên nút chọn ngày
    private fun updateDateButtonText() {
        val calendar = Calendar.getInstance()
        calendar.time = selectedDate

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // Tháng bắt đầu từ 0
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateString = "$day/$month/$year"
        findViewById<Button>(R.id.datePickerButton).text = "Ngày: $dateString"
    }

    // Sắp xếp nhiệm vụ theo tên
    private fun sortTasksByName() {
        tasks.sortBy { it.name }
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "Đã sắp xếp theo tên", Toast.LENGTH_SHORT).show()
    }

    // Sắp xếp nhiệm vụ theo ngày
    private fun sortTasksByDate() {
        tasks.sortBy { it.dueDate }
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "Đã sắp xếp theo ngày", Toast.LENGTH_SHORT).show()
    }
}