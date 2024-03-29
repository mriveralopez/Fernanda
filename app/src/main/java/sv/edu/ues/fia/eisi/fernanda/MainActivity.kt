package sv.edu.ues.fia.eisi.fernanda

import ai.api.AIListener
import ai.api.android.AIConfiguration
import ai.api.android.AIService
import ai.api.model.AIError
import ai.api.model.AIResponse
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context

class MainActivity : AppCompatActivity(), AIListener, TextToSpeech.OnInitListener {

    override fun onInit(status: Int) {

    }

    override fun onResult(result: AIResponse?) {
        val respuesta = result?.result
        val escuchado = respuesta?.resolvedQuery
        val responder = respuesta?.fulfillment?.speech

        try {
            probarFunciones(escuchado, responder)
        } catch (e: Exception) {
            reemplazarTextos(escuchado, "Lo siento no he logrado resolver tu peticion, intenta de nuevo mas tarde!")
        }
    }

    fun probarFunciones(escuchado: String?, respuesta: String?) {
        var delimiter = "func"

        if (respuesta != null) {
            var respues = respuesta
            val parts = respues.split(delimiter)

            if (parts != null && escuchado != null) {
                if (parts.size > 1) {
                    funciones(escuchado, parts[0], parts[1])
                } else {
                    reemplazarTextos(escuchado, respuesta)
                }
            } else {
                reemplazarTextos(escuchado, respuesta)
            }
        } else {
            reemplazarTextos(escuchado, respuesta)
        }
    }

    fun funciones(escuchado: String, funcion: String, variables: String) {
        if(funcion == "sum"){
            val parentesisAbrir = "("
            val parentesisCerrar = ")"
            val newValue = ""

            var sinParentesisAbrir = variables.replace(parentesisAbrir, newValue)
            var sinParetesisCerrar = sinParentesisAbrir.replace(parentesisCerrar, newValue)

            var delimiter = "&"
            val parts = sinParetesisCerrar.split(delimiter)

            var res = 0.0

            if (parts.size > 1) {
                res = parts[0].toDouble() + parts[1].toDouble()
            }

            val resp = "" + res

            reemplazarTextos(escuchado, resp)
        }
        if(funcion == "res"){
            val parentesisAbrir = "("
            val parentesisCerrar = ")"
            val newValue = ""

            var sinParentesisAbrir = variables.replace(parentesisAbrir, newValue)
            var sinParetesisCerrar = sinParentesisAbrir.replace(parentesisCerrar, newValue)

            var delimiter = "&"
            val parts = sinParetesisCerrar.split(delimiter)

            var res = 0.0

            if (parts.size > 1) {
                res = parts[0].toDouble() - parts[1].toDouble()
            }

            val resp = "" + res

            reemplazarTextos(escuchado, resp)
        }
        if(funcion == "mult"){
            val parentesisAbrir = "("
            val parentesisCerrar = ")"
            val newValue = ""

            var sinParentesisAbrir = variables.replace(parentesisAbrir, newValue)
            var sinParetesisCerrar = sinParentesisAbrir.replace(parentesisCerrar, newValue)

            var delimiter = "&"
            val parts = sinParetesisCerrar.split(delimiter)

            var res = 0.0

            if (parts.size > 1) {
                res = parts[0].toDouble() * parts[1].toDouble()
            }

            val resp = "" + res

            reemplazarTextos(escuchado, resp)
        }
        if(funcion == "div"){
            val parentesisAbrir = "("
            val parentesisCerrar = ")"
            val newValue = ""

            var sinParentesisAbrir = variables.replace(parentesisAbrir, newValue)
            var sinParetesisCerrar = sinParentesisAbrir.replace(parentesisCerrar, newValue)

            var delimiter = "&"
            val parts = sinParetesisCerrar.split(delimiter)

            var res = 0.0

            if (parts.size > 1) {
                res = parts[0].toDouble() / parts[1].toDouble()
            }

            val resp = "" + res

            reemplazarTextos(escuchado, resp)
        }
        if (funcion == "abrir") {
            val parentesisAbrir = "("
            val parentesisCerrar = ")"
            val newValue = ""

            var sinParentesisAbrir = variables.replace(parentesisAbrir, newValue)
            var app = sinParentesisAbrir.replace(parentesisCerrar, newValue)
            abrirApp(escuchado, app.toLowerCase())
        }
        if (funcion == "llamarpornombre") {
            val parentesisAbrir = "("
            val parentesisCerrar = ")"
            val newValue = ""

            var sinParentesisAbrir = variables.replace(parentesisAbrir, newValue)
            var nombre = sinParentesisAbrir.replace(parentesisCerrar, newValue)

//            try {
                var db: ControlDB
                db = ControlDB(this)
                var numero = db.buscarContacto(nombre)

                if (numero.length > 0) {
                    reemplazarTextos(escuchado, "Llamando")

                    val i = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numero));
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED
                    )
                        return
                    startActivity(i);
                } else {
                    reemplazarTextos(escuchado, "No se encontro el numero de " + nombre)
                }
//            } catch (e: Exception) {
//                reemplazarTextos(escuchado, "No se encontro el numero de " + nombre)
//            }
        }
        if (funcion == "llamarpornumero") {
            val parentesisAbrir = "("
            val parentesisCerrar = ")"
            val newValue = ""

            var sinParentesisAbrir = variables.replace(parentesisAbrir, newValue)
            var numero = sinParentesisAbrir.replace(parentesisCerrar, newValue)

            reemplazarTextos(escuchado, "Llamando")

            val i = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numero));
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                return
            startActivity(i);
        }
        if (funcion == "smsbyphonenumber") {
//            val parentesisAbrir = "("
//            val parentesisCerrar = ")"
//            val newValue = ""
//
//            var sinParentesisAbrir = variables.replace(parentesisAbrir, newValue)
//            var numero = sinParentesisAbrir.replace(parentesisCerrar, newValue)
//
//            reemplazarTextos(escuchado, "Que mensaje deseas enviar?")
//
//            val habla = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//            habla.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX")
//            habla.putExtra("number", numero)
//            startActivityForResult(habla, SMS)
        }
        if (funcion == "writesmsbyphonenumber") {
            val parentesisAbrir = "("
            val parentesisCerrar = ")"
            val newValue = ""

            var sinParentesisAbrir = variables.replace(parentesisAbrir, newValue)
            var numero = sinParentesisAbrir.replace(parentesisCerrar, newValue)

            reemplazarTextos(escuchado, "Escriba el mensaje a continuacion")

            val uri = Uri.parse("smsto:" + numero)
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", "Escribe tu mensaje aquí...")
            startActivity(intent)
        }
        if (funcion == "writesmsbyname") {
            val parentesisAbrir = "("
            val parentesisCerrar = ")"
            val newValue = ""

            var sinParentesisAbrir = variables.replace(parentesisAbrir, newValue)
            var nombre = sinParentesisAbrir.replace(parentesisCerrar, newValue)

            try {
                var db: ControlDB
                db = ControlDB(this)
                var numero = db.buscarContacto(nombre)

                if (numero.length > 0) {
                    reemplazarTextos(escuchado, "Escriba el mensaje a continuacion")

                    val uri = Uri.parse("smsto:" + numero)
                    val intent = Intent(Intent.ACTION_SENDTO, uri)
                    intent.putExtra("sms_body", "Escribe tu mensaje aquí...")
                    startActivity(intent)
                } else {
                    reemplazarTextos(escuchado, "No se encontro el numero de " + nombre)
                }
            } catch (e: Exception) {
                reemplazarTextos(escuchado, "No se encontro el numero de " + nombre)
            }
        }
        if (funcion == "createcontact") {
            val parentesisAbrir = "("
            val parentesisCerrar = ")"
            val newValue = ""

            var sinParentesisAbrir = variables.replace(parentesisAbrir, newValue)
            var sinParetesisCerrar = sinParentesisAbrir.replace(parentesisCerrar, newValue)

            var delimiter = "&"
            val parts = sinParetesisCerrar.split(delimiter)

            try {
                if (parts.size > 1) {
                    var db: ControlDB
                    db = ControlDB(this)
                    if (db.agregarContacto(parts[0], parts[1])) {
                        reemplazarTextos(escuchado, "Contacto agregado")
                    } else {
                        reemplazarTextos(escuchado, "Error al guardar")
                    }
                } else {
                    reemplazarTextos(escuchado, "Error al guardar")
                }
            } catch (e: java.lang.Exception) {
                reemplazarTextos(escuchado, "Error al guardar")
            }
        }
        if (funcion == "alarm") {
            val parentesisAbrir = "("
            val parentesisCerrar = ")"
            val newValue = ""

            var sinParentesisAbrir = variables.replace(parentesisAbrir, newValue)
            var time = sinParentesisAbrir.replace(parentesisCerrar, newValue)

            var delimiter = ":"
            val parts = time.split(delimiter)

            val c = Calendar.getInstance()
            c.set(Calendar.HOUR_OF_DAY, parts[0].toInt())
            c.set(Calendar.MINUTE, parts[1].toInt())
            c.set(Calendar.SECOND, parts[2].toInt())

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, AlertReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)

            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1)
            }

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)

            reemplazarTextos(escuchado, "Alarma creada a las " + time)
        }
        if (funcion == "email") {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // only email apps should handle this
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == Activity.RESULT_OK && requestCode == SMS) {
//            val reconocido = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
//            val escuchado = reconocido[0]
//
//            val smsManager = SmsManager.getDefault() as SmsManager
//            Toast.makeText(this, data.extras.getString("number") + escuchado, Toast.LENGTH_LONG)
//            //smsManager.sendTextMessage("1234", null, escuchado, null, null)
//        }
//    }

    fun abrirApp(escuchado: String, app: String) {
        reemplazarTextos(escuchado, "")
        if (app == "camara") {
            reemplazarTextos(escuchado, "Abriendo Camara")
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, FOTOGRAFIA)
        } else if (app == "whatsapp") {
            reemplazarTextos(escuchado, "Abriendo WhatsApp")
            val launchIntent = packageManager.getLaunchIntentForPackage("com.whatsapp")
            startActivity(launchIntent)
        } else if (app == "uber") {
            reemplazarTextos(escuchado, "Abriendo Uber")
            val launchIntent = packageManager.getLaunchIntentForPackage("com.ubercab")
            startActivity(launchIntent)
        }
        else if (app == "facebook") {
            reemplazarTextos(escuchado, "Abriendo Facebook")
            try {
                val launchIntent = packageManager.getLaunchIntentForPackage("com.facebook.katana")
                startActivity(launchIntent)
            } catch (e: Exception) {
                val launchIntent = packageManager.getLaunchIntentForPackage("com.facebook.lite")
                startActivity(launchIntent)
            }
        } else if (app == "instagram") {
            reemplazarTextos(escuchado, "Abriendo Instagram")
            val launchIntent = packageManager.getLaunchIntentForPackage("com.instagram.android")
            startActivity(launchIntent)
        } else if (app == "contactos") {
            reemplazarTextos(escuchado, "Abriendo Contactos")
            val launchIntent = packageManager.getLaunchIntentForPackage("com.android.contacts")
            startActivity(launchIntent)
        } else if (app == "play store") {
            reemplazarTextos(escuchado, "Abriendo PlayStore")
            val prueba = Intent(android.content.Intent.ACTION_VIEW)
            prueba.data = Uri.parse("https://play.google.com/store/")
            startActivity(prueba)
        } else if (app == "youtube") {
            reemplazarTextos(escuchado, "Abriendo YouTube")
            val launchIntent = packageManager.getLaunchIntentForPackage("com.google.android.youtube")
            startActivity(launchIntent)
        }
    }

    fun reemplazarTextos(escuchado: String?, respuesta: String?) {
        tv_escuchado.text = escuchado
        tv_respuesta.text = respuesta

        hablar(respuesta)
    }

    fun hablar(respuesta: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            leer?.speak(respuesta, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            //leer?.speak(respuesta, TextToSpeech.QUEUE_FLUSH, null)
        }
    }

    override fun onListeningStarted() {
    }

    override fun onAudioLevel(level: Float) {

    }

    override fun onError(error: AIError?) {
        val err = "Hubo un error!"
        reemplazarTextos(err, err)
    }

    override fun onListeningCanceled() {

    }

    override fun onListeningFinished() {
    }

    val accessToken = "db891e5debfa4f0481c9f0a5c30520c9"
    //val ppp = "9c5efa6fd16c4fb6ac4e60e2b0709285"
    val accessDevToken = "912534be08a0411cb1396a2582db3460"
    val REQUEST = 200
    var leer: TextToSpeech? = null
    var FOTOGRAFIA = 654
    private val SMS = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        leer = TextToSpeech(
            this,
            this)

        validar()
        configAsistente()
    }

    fun configAsistente() {

        var db : ControlDB
        db = ControlDB(this)
        db.llenarBase()

        val configuracion = AIConfiguration(
            accessToken,
            ai.api.AIConfiguration.SupportedLanguages.Spanish,
            AIConfiguration.RecognitionEngine.System
        )
        val service = AIService.getService(
            this,
            configuracion
        )
        service.setListener(
            this
        )

        micButton.setOnClickListener {
            service.startListening()
        }
    }

    fun validar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            solicitarPermiso()
        }
    }

    fun solicitarPermiso() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), REQUEST)
        }
    }
}
