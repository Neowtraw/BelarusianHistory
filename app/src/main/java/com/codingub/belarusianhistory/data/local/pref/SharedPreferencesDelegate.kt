import android.content.Context
import android.content.SharedPreferences
import com.codingub.belarusianhistory.App
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPreferencesDelegate<T : Any>(
    private val context: Context,
    private val name: String,
    private val defaultValue: T
) : ReadWriteProperty<Any?, T> {

    private val sharedPreferences by lazy {
        context.getSharedPreferences("${App.getInstance().packageName}_${this::class.java.simpleName}",
            Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return sharedPreferences.run {
            when (defaultValue) {
                is Boolean -> getBoolean(name, defaultValue)
                is Int -> getInt(name, defaultValue)
                is Long -> getLong(name, defaultValue)
                is Float -> getFloat(name, defaultValue)
                is String -> getString(name, defaultValue)
                else -> throw IllegalArgumentException("Unsupported type")
            } as T
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        sharedPreferences.edit()
            .apply { putValue(name, value) }
            .apply()
    }

    private fun SharedPreferences.Editor.putValue(key: String, value: T) {
        when (value) {
            is Boolean -> putBoolean(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is String -> putString(key, value)
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
}