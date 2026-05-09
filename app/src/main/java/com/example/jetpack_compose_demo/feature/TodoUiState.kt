import com.example.jetpack_compose_demo.domain.model.Todo

data class TodoUiState(
    val todos : List<Todo> = emptyList()
)

sealed class TodoIntent {
    data class AddTodo(val title: String, val desc: String) : TodoIntent()
    data class ToggleTodo(val todo: Todo) : TodoIntent()
    data class DeleteTodo(val todo: Todo) : TodoIntent()
    object LoadTodos : TodoIntent()
}