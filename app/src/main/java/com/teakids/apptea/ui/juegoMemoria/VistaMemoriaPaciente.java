package com.teakids.apptea.ui.juegoMemoria;


import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.teakids.apptea.R;
import com.teakids.apptea.ui.DetalleCategoriaJuego.JuegoViewModel;
import com.teakids.apptea.ui.juego.OpcionViewModel;
import com.teakids.apptea.ui.juego.PreguntaViewModel;
import com.teakids.apptea.ui.juegoSeleccion.DetalleResultadoViewModel;
import com.teakids.apptea.ui.pictograma.PictogramaViewModel;
import com.teakids.apptea.utilidades.AdministarSesion;
import com.teakids.apptea.utilidades.TTSManager;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import roomsqlite.dao.ResultadoDao;
import roomsqlite.database.ImageConverter;
import roomsqlite.database.appDatabase;
import roomsqlite.entidades.DetalleResultado;
import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Opcion;
import roomsqlite.entidades.Pictograma;
import roomsqlite.entidades.Pregunta;
import roomsqlite.entidades.Resultado;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VistaMemoriaPaciente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VistaMemoriaPaciente extends Fragment {



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView nombreCard1;
    private TextView nombreCard2;
    private TextView nombreCard3;
    private TextView nombreCard4;
    private TextView nombreCard5;
    private TextView nombreCard6;
    private TextView nombreJuego;
    private ImageView imgCard1;
    private ImageView imgCard2;
    private ImageView imgCard3;
    private ImageView imgCard4;
    private ImageView imgCard5;
    private ImageView imgCard6;
    private MaterialCardView opcion1;
    private MaterialCardView opcion2;
    private MaterialCardView opcion3;
    private MaterialCardView opcion4;
    private MaterialCardView opcion5;
    private MaterialCardView opcion6;
    Juego juego = new Juego();
    int juegoId = 0;
    int contador = 0;
    int posicion = 0;
    int longitudPreguntas;
    boolean  bandera1, bandera2, bandera3, bandera4, duracion=false;
    String titulo;
    LiveData<List<Pregunta>> listadoPreguntas;
    LiveData<List<Opcion>> listaOpciones;
    LiveData<Pictograma> pictograma;
    JuegoViewModel juegoViewModel;
    PreguntaViewModel preguntaViewModel;
    OpcionViewModel opcionViewModel;
    PictogramaViewModel pictogramaViewModel;
    LottieAnimationView celebracion;
    public static int pic1,pic2,pic3,pic4,pic5,pic6;
    int seleccion1 = 0;
    int seleccion2 = 0;
    int ca;
    int pos =0;
    int correctas = 0;
    int incorrectas = 0;
    private int milisegundos = 800;
    TTSManager ttsManager = null;
    Button siguiente;
    DetalleResultado detalleResultado= new DetalleResultado();
    DetalleResultadoViewModel detalleResultadoViewModel;
    AdministarSesion sesion;
    int resultado;
    ResultadoDao resultadoDao;
    Resultado res = new Resultado();
    private static Drawable fondoBlanco;
    private static Drawable fondo;

    public VistaMemoriaPaciente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VistaMemoriaPaciente.
     */
    // TODO: Rename and change types and number of parameters
    public static VistaMemoriaPaciente newInstance(String param1, String param2) {
        VistaMemoriaPaciente fragment = new VistaMemoriaPaciente();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vista_memoria_paciente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle argumento = getArguments();
        nombreCard1 = view.findViewById(R.id.nombre_card1);
        nombreCard2 = view.findViewById(R.id.nombre_card2);
        nombreCard3 = view.findViewById(R.id.nombre_card3);
        nombreCard4 = view.findViewById(R.id.nombre_card4);
        nombreCard5 = view.findViewById(R.id.nombre_card5);
        nombreCard6 = view.findViewById(R.id.nombre_card6);
        imgCard1 = view.findViewById(R.id.img_card1);
        imgCard2 = view.findViewById(R.id.img_card2);
        imgCard3 = view.findViewById(R.id.img_card3);
        imgCard4 = view.findViewById(R.id.img_card4);
        imgCard5 = view.findViewById(R.id.img_card5);
        imgCard6 = view.findViewById(R.id.img_card6);
        opcion1 = view.findViewById(R.id.card_memoria1);
        opcion2 = view.findViewById(R.id.card_memoria2);
        opcion3 = view.findViewById(R.id.card_memoria3);
        opcion4 = view.findViewById(R.id.card_memoria4);
        opcion5 = view.findViewById(R.id.card_memoria5);
        opcion6 = view.findViewById(R.id.card_memoria6);
        siguiente = view.findViewById(R.id.pregunta_siguiente);
        celebracion = view.findViewById(R.id.lottie_acierto);
        nombreJuego = view.findViewById(R.id.titulo_juego);
        preguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);
        opcionViewModel = new ViewModelProvider(this).get(OpcionViewModel.class);
        pictogramaViewModel = new ViewModelProvider(this).get(PictogramaViewModel.class);
        juegoViewModel = new ViewModelProvider(this).get(JuegoViewModel.class);
        detalleResultadoViewModel = new ViewModelProvider(this).get(DetalleResultadoViewModel.class);
        resultadoDao= appDatabase.getDatabase(getActivity()).resultadoDao();
        ttsManager = new TTSManager();
        ttsManager.init(getActivity());
        resultado = argumento.getInt("resultadoId");
        siguiente.setVisibility(View.INVISIBLE);
        celebracion.setVisibility(View.INVISIBLE);
        sesion = new AdministarSesion(getActivity());
        juego = (Juego) argumento.getSerializable("juego");
        //juegoId = argumento.getInt("juegoId",0);
        //titulo = argumento.getString("nombreJuego");

        if(sesion.getIdioma()==1){
            nombreJuego.setText(juego.getJuego_nombre());
        }else{
            nombreJuego.setText(juego.getName_game());
        }


        fondoBlanco = getResources().getDrawable(R.drawable.ic_add_black_24dp);
        fondo = getResources().getDrawable(R.drawable.instalacion2);




        listadoPreguntas = preguntaViewModel.getPreguntasByIdJuego(juego.getJuego_id());
        listadoPreguntas.observe(getActivity(), preguntas -> {
            longitudPreguntas = preguntas.size();
        });

        setearOpciones(posicion);



        opcion1.setOnClickListener(v -> {
            ttsManager.initQueue(nombreCard1.getText().toString());
            imgCard1.setVisibility(View.VISIBLE);
            nombreCard1.setVisibility(View.VISIBLE);

            if(seleccion1 == 0) {
                seleccion1 = pic1;
                pos = 1;
                opcion1.setEnabled(false);
                opcion1.setBackgroundDrawable(fondoBlanco);
            }else{
                opcion1.setEnabled(false);
                opcion1.setBackgroundDrawable(fondoBlanco);
                seleccion2 = pic1;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(compararCartas(seleccion1,seleccion2)==false){
                            opcion1.setEnabled(true);
                            validar(pos);
                            imgCard1.setVisibility(View.INVISIBLE);
                            opcion1.setBackgroundDrawable(fondo);
                            nombreCard1.setVisibility(View.INVISIBLE);
                        }else{
                            opcion1.setEnabled(false);
                        }
                    }
                },milisegundos);

            }

        });

        opcion2.setOnClickListener(v -> {
            ttsManager.initQueue(nombreCard2.getText().toString());
            imgCard2.setVisibility(View.VISIBLE);
            nombreCard2.setVisibility(View.VISIBLE);

            if(seleccion1 == 0) {
                seleccion1 = pic2;
                pos = 2;
                opcion2.setEnabled(false);
                opcion2.setBackgroundDrawable(fondoBlanco);
            }else{
                opcion2.setEnabled(false);
                opcion2.setBackgroundDrawable(fondoBlanco);
                seleccion2 = pic2;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(compararCartas(seleccion1,seleccion2)==false){
                            opcion2.setEnabled(true);
                            opcion2.setBackgroundDrawable(fondo);
                            validar(pos);
                            imgCard2.setVisibility(View.INVISIBLE);
                            nombreCard2.setVisibility(View.INVISIBLE);
                        }else{
                            opcion2.setEnabled(false);
                        };
                    }
                },milisegundos);
            }
            System.out.println(pic2);
            }
        );

        opcion3.setOnClickListener(v -> {
            ttsManager.initQueue(nombreCard3.getText().toString());
            imgCard3.setVisibility(View.VISIBLE);
            nombreCard3.setVisibility(View.VISIBLE);

            if(seleccion1 == 0) {
                seleccion1 = pic3;
                pos = 3;
                opcion3.setEnabled(false);
                opcion3.setBackgroundDrawable(fondoBlanco);
            }else{
                opcion3.setEnabled(false);
                opcion3.setBackgroundDrawable(fondoBlanco);
                seleccion2 = pic3;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(compararCartas(seleccion1,seleccion2)==false){
                            opcion3.setEnabled(true);
                            opcion3.setBackgroundDrawable(fondo);
                            validar(pos);
                            imgCard3.setVisibility(View.INVISIBLE);
                            nombreCard3.setVisibility(View.INVISIBLE);
                        }else{
                            opcion3.setEnabled(false);
                        };
                    }
                },milisegundos);

            }

                }
        );
        opcion4.setOnClickListener(v -> {
            ttsManager.initQueue(nombreCard4.getText().toString());
            imgCard4.setVisibility(View.VISIBLE);
            nombreCard4.setVisibility(View.VISIBLE);

            if(seleccion1 == 0) {
                seleccion1 = pic4;
                pos = 4;
                opcion4.setEnabled(false);
                opcion4.setBackgroundDrawable(fondoBlanco);
            }else{
                opcion4.setEnabled(false);
                opcion4.setBackgroundDrawable(fondoBlanco);
                seleccion2 = pic4;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(compararCartas(seleccion1,seleccion2)==false){
                            opcion4.setEnabled(true);
                            validar(pos);
                            opcion4.setBackgroundDrawable(fondo);
                            imgCard4.setVisibility(View.INVISIBLE);
                            nombreCard4.setVisibility(View.INVISIBLE);
                        }else{
                            opcion4.setEnabled(false);
                        };
                    }
                },milisegundos);
            }

                }
        );
        opcion5.setOnClickListener(v -> {
            ttsManager.initQueue(nombreCard5.getText().toString());
            imgCard5.setVisibility(View.VISIBLE);
            nombreCard5.setVisibility(View.VISIBLE);

            if(seleccion1 == 0) {
                seleccion1 = pic5;
                pos = 5;
                opcion5.setEnabled(false);
                opcion5.setBackgroundDrawable(fondoBlanco);
            }else{
                opcion5.setEnabled(false);
                opcion5.setBackgroundDrawable(fondoBlanco);
                seleccion2 = pic5;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(compararCartas(seleccion1,seleccion2)==false){
                            opcion5.setEnabled(true);
                            opcion5.setBackgroundDrawable(fondo);
                            validar(pos);
                            imgCard5.setVisibility(View.INVISIBLE);
                            nombreCard5.setVisibility(View.INVISIBLE);
                        }else{
                            opcion5.setEnabled(false);
                        };
                    }
                },milisegundos);
            }

                }
        );
        opcion6.setOnClickListener(v -> {
            ttsManager.initQueue(nombreCard6.getText().toString());
            imgCard6.setVisibility(View.VISIBLE);
            nombreCard6.setVisibility(View.VISIBLE);

            if(seleccion1 == 0) {
                seleccion1 = pic6;
                pos = 6;
                opcion6.setEnabled(false);
                opcion6.setBackgroundDrawable(fondoBlanco);
            }else{
                opcion6.setEnabled(false);
                opcion6.setBackgroundDrawable(fondoBlanco);
                seleccion2 = pic6;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(compararCartas(seleccion1,seleccion2)==false){
                            opcion6.setEnabled(true);
                            opcion6.setBackgroundDrawable(fondo);
                            validar(pos);
                            imgCard6.setVisibility(View.INVISIBLE);
                            nombreCard6.setVisibility(View.INVISIBLE);
                        }else{
                            opcion6.setEnabled(false);
                        };
                    }
                },milisegundos);
            }

                }
        );

        siguiente.setOnClickListener(v -> {
            posicion++;

            //Se comprueba el tipo de usuario Si es 1 guardamos resultado si es distinto no se guarda.
            if(sesion.obtenerTipoUsuario()==1) {
                detalleResultado.setResultado_id(resultado);
                detalleResultado.setNombre_pregunta(getResources().getString(R.string.nivel)+" "+posicion+":"); //poner string
                detalleResultado.setCantidad_fallos(incorrectas);
                detalleResultadoViewModel.insertResultado(detalleResultado);
            }else{

            }

            // If para validar si existen mas preguntas sino se envia al fragment de fin de juego
                if (posicion <= longitudPreguntas - 1) {
                    setearOpciones(posicion);
                    reiniciarCartas();
                    incorrectas=0;
                } else {
                    Bundle bundleEnvio = new Bundle();
                    res = resultadoDao.obtenerResultado();
                    FinJuegoMemoriaFragment finJuegoMemoriaFragment = new FinJuegoMemoriaFragment();
                    bundleEnvio.putSerializable("resultado", res);
                    bundleEnvio.putInt("categoriaJuego", juego.getCategoria_juego_id());
                    bundleEnvio.putBoolean("bandera",argumento.getBoolean("bandera"));
                    finJuegoMemoriaFragment.setArguments(bundleEnvio);
                    Navigation.findNavController(v).navigate(R.id.finJuegoMemoriaFragment,bundleEnvio);

                    }


        });


    }


    //Metodo para validar la primera seleccion del usuario
    // SI las cartas no son iguales estas deben ocultarse de nuevo
    private void validar(int pos){
        switch(pos){
            case 1:
                opcion1.setEnabled(true);
                opcion1.setBackgroundDrawable(fondo);
                imgCard1.setVisibility(View.INVISIBLE);
                nombreCard1.setVisibility(View.INVISIBLE);
                break;
            case 2:
                opcion2.setEnabled(true);
                imgCard2.setVisibility(View.INVISIBLE);
                nombreCard2.setVisibility(View.INVISIBLE);
                opcion2.setBackgroundDrawable(fondo);
                break;
            case 3:
                opcion3.setEnabled(true);
                imgCard3.setVisibility(View.INVISIBLE);
                nombreCard3.setVisibility(View.INVISIBLE);
                opcion3.setBackgroundDrawable(fondo);
                break;
            case 4:
                opcion4.setEnabled(true);
                imgCard4.setVisibility(View.INVISIBLE);
                nombreCard4.setVisibility(View.INVISIBLE);
                opcion4.setBackgroundDrawable(fondo);
                break;
            case 5:
                opcion5.setEnabled(true);
                imgCard5.setVisibility(View.INVISIBLE);
                nombreCard5.setVisibility(View.INVISIBLE);
                opcion5.setBackgroundDrawable(fondo);
                break;
            case 6:
                opcion6.setEnabled(true);
                imgCard6.setVisibility(View.INVISIBLE);
                nombreCard6.setVisibility(View.INVISIBLE);
                opcion6.setBackgroundDrawable(fondo);
                break;
        }
    }

    //Metodo para comparar las dos selecciones del usuario
    //Retorna true si son iguales y False sino son iguales
    private boolean compararCartas(int s1,int s2){
        boolean iguales;
        if(s1 == s2){
            System.out.println("iguales");
            iguales = true;
            correctas++;
            celebracion.setVisibility(View.VISIBLE);
            celebracion.bringToFront();
            celebracion.setSpeed(2);
            celebracion.playAnimation();
            ttsManager.initQueue(getResources().getString(R.string.bienHecho));
            if(correctas == 3){
                siguiente.setVisibility(View.VISIBLE);
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    celebracion.setVisibility(View.INVISIBLE);
                }
            },milisegundos);


        }else{
            System.out.println("no son Iguales");
            incorrectas++;
            iguales = false;
        }
        seleccion1 = 0;
        seleccion2 = 0;
        return iguales;
    }


    //Metodo para reiniciar cartas se vuelven habilitar
    private void reiniciarCartas(){
        siguiente.setVisibility(View.INVISIBLE);
        opcion1.setEnabled(true);
        opcion2.setEnabled(true);
        opcion3.setEnabled(true);
        opcion4.setEnabled(true);
        opcion5.setEnabled(true);
        opcion6.setEnabled(true);
    }



    //funcion que setea cardviews con pictogramas, recibe la posicion en la lista de las preguntas
    //SETEA CANTIDAD DE RESPUESTAS BUENAS POR PREGUNTA
    @SuppressLint("ResourceAsColor")
    private void setearOpciones(int posicion) {

        correctas = 0;
        siguiente.setVisibility(View.INVISIBLE);
        listadoPreguntas.observe(getActivity(), preguntas -> {
            listaOpciones = opcionViewModel.getOcionesByIdPregunta(preguntas.get(posicion).getPregunta_id());
            listaOpciones.observe(getActivity(), opciones -> {
                List<Opcion> nuevaListaOpcion = ordenarAleatoriamente(opciones);
                int size = (nuevaListaOpcion.size()) - 1;
                int count = contador;
                //seteado de las opciones
                while (count <= size) {
                    pictograma = pictogramaViewModel.getPictogramaById(nuevaListaOpcion.get(count).getPictograma_id());
                    //seteado de imagenes y texto del pictograma


                    int finalCount = count;
                    pictograma.observe(getActivity(), pictograma1 -> {
                        switch (finalCount) {
                            case 0:
                                imgCard1.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma1.getPictograma_imagen()));
                                if(sesion.getIdioma()==1){
                                    nombreCard1.setText(pictograma1.getPictograma_nombre());
                                }else{
                                    nombreCard1.setText(pictograma1.getPictograma_name());}

                                bandera1 = opciones.get(0).isOpcion_respuesta();
                                opcion1.setVisibility(View.VISIBLE);
                                opcion1.setBackgroundDrawable(fondo);
                                imgCard1.setVisibility(View.INVISIBLE);
                                nombreCard1.setVisibility(View.INVISIBLE);
                                pic1 = pictograma1.getPictograma_id();
                                break;
                            case 1:
                                imgCard2.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma1.getPictograma_imagen()));
                                if(sesion.getIdioma()==1){
                                    nombreCard2.setText(pictograma1.getPictograma_nombre());
                                }else{
                                    nombreCard2.setText(pictograma1.getPictograma_name());}

                                bandera2 = opciones.get(1).isOpcion_respuesta();
                                opcion2.setVisibility(View.VISIBLE);
                                opcion2.setBackgroundDrawable(fondo);
                                imgCard2.setVisibility(View.INVISIBLE);
                                nombreCard2.setVisibility(View.INVISIBLE);
                                pic2 = pictograma1.getPictograma_id();
                                break;
                            case 2:
                                imgCard3.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma1.getPictograma_imagen()));
                                if(sesion.getIdioma()==1){
                                    nombreCard3.setText(pictograma1.getPictograma_nombre());
                                }else{
                                    nombreCard3.setText(pictograma1.getPictograma_name());}

                                bandera3 = opciones.get(2).isOpcion_respuesta();
                                opcion3.setVisibility(View.VISIBLE);
                                opcion3.setBackgroundDrawable(fondo);
                                imgCard3.setVisibility(View.INVISIBLE);
                                nombreCard3.setVisibility(View.INVISIBLE);
                                pic3 = pictograma1.getPictograma_id();
                                break;
                            case 3:
                                imgCard4.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma1.getPictograma_imagen()));
                                if(sesion.getIdioma()==1){
                                    nombreCard4.setText(pictograma1.getPictograma_nombre());
                                }else{
                                    nombreCard4.setText(pictograma1.getPictograma_name());}

                                bandera4 = opciones.get(3).isOpcion_respuesta();
                                opcion4.setVisibility(View.VISIBLE);
                                opcion4.setBackgroundDrawable(fondo);
                                imgCard4.setVisibility(View.INVISIBLE);
                                nombreCard4.setVisibility(View.INVISIBLE);
                                pic4 = pictograma1.getPictograma_id();
                                break;
                            case 4:
                                imgCard5.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma1.getPictograma_imagen()));
                                if(sesion.getIdioma()==1){
                                    nombreCard5.setText(pictograma1.getPictograma_nombre());
                                }else{
                                    nombreCard5.setText(pictograma1.getPictograma_name());}

                                bandera3 = opciones.get(4).isOpcion_respuesta();
                                imgCard5.setVisibility(View.INVISIBLE);
                                nombreCard5.setVisibility(View.INVISIBLE);
                                opcion5.setVisibility(View.VISIBLE);
                                opcion5.setBackgroundDrawable(fondo);
                                pic5 = pictograma1.getPictograma_id();
                                break;
                            case 5:
                                imgCard6.setImageBitmap(ImageConverter.convertirByteArrayAImagen(pictograma1.getPictograma_imagen()));
                                if(sesion.getIdioma()==1){
                                    nombreCard6.setText(pictograma1.getPictograma_nombre());
                                }else{
                                    nombreCard6.setText(pictograma1.getPictograma_name());}

                                bandera3 = opciones.get(5).isOpcion_respuesta();
                                opcion6.setVisibility(View.VISIBLE);
                                opcion6.setBackgroundDrawable(fondo);
                                imgCard6.setVisibility(View.INVISIBLE);
                                nombreCard6.setVisibility(View.INVISIBLE);
                                pic6 = pictograma1.getPictograma_id();
                                break;
                        }
                    });
                    count++;
                }

            });
        });


    }

    //funcion que devuelve una lista ordenada de forma aleatoria de opciones
    private List<Opcion> ordenarAleatoriamente(List<Opcion> opciones) {
        /* Función de barajamiento usando el algoritmo Fisher Yates
         *  Se recibe un arreglo de Preguntas (ordenado o no) y se aplica
         *  el algoritmo de Fisher - Yates
         *
         *  Se devuelve un arreglo de preguntas desordenado aleatoriamente
         */
        for (int i = opciones.size() - 1; i > 0; i--) {
            int indice = (int) Math.floor(Math.random() * (i + 1));
            Opcion tmp = opciones.get(i);
            opciones.set(i, opciones.get(indice));
            opciones.set(indice, tmp);
        }

        return opciones;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }


}
