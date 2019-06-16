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
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), AIListener, TextToSpeech.OnInitListener {

    fun prueba(view: View) {
        funciones("Escribir mensaje al 72274441","writesmsbyphonenumber", "(72274441)")
    }

    override fun onInit(status: Int) {

    }

    override fun onResult(result: AIResponse?) {
        val respuesta = result?.result
        val escuchado = respuesta?.resolvedQuery
        val responder = respuesta?.fulfillment?.speech

        tv_res.text = responder

        probarFunciones(escuchado, responder)
        //reemplazarTextos(escuchado,responder)
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
        if (funcion == "llamarpornumero") {
            val parentesisAbrir = "("
            val parentesisCerrar = ")"
            val newValue = ""

            var sinParentesisAbrir = variables.replace(parentesisAbrir, newValue)
            var numero = sinParentesisAbrir.replace(parentesisCerrar, newValue)

            reemplazarTextos(escuchado, "Llamando al " + numero)

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

            reemplazarTextos(escuchado, "Esciba el mensaje a continuacion al " + numero)

            val uri = Uri.parse("smsto:" + numero)
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", "Escribe tu mensaje aquí...")
            startActivity(intent)
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
                val launchIntent = packageManager.getLaunchIntentForPackage("com.facebook")
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
