package com.example.crrlo.tictac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;
    String tablero[][];
    Button tableroB[][];
    Button nuevoJuego;
    int casillas_disponibles;
    boolean x;
    boolean gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        casillas_disponibles = 9;
        tablero = new String[3][3];
        tableroB = new Button[3][3];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                tablero[i][j] = "-";
            }
        }

        tableroB[0][0] = (Button) findViewById(R.id.button);
        tableroB[0][1] = (Button) findViewById(R.id.button2);
        tableroB[0][2] = (Button) findViewById(R.id.button3);
        tableroB[1][0] = (Button) findViewById(R.id.button4);
        tableroB[1][1] = (Button) findViewById(R.id.button5);
        tableroB[1][2] = (Button) findViewById(R.id.button6);
        tableroB[2][0] = (Button) findViewById(R.id.button7);
        tableroB[2][1] = (Button) findViewById(R.id.button8);
        tableroB[2][2] = (Button) findViewById(R.id.button9);


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int finalI = i;
                final int finalJ = j;
                tableroB[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        casillas_disponibles--;
                        tableroB[finalI][finalJ].setText("X");
                        tablero[finalI][finalJ] = "x";
                        miniMax(1, casillas_disponibles, x, tablero.clone());
                        switch (hayGanador()) {
                            case 1: {
                                Toast.makeText(MainActivity.this, "Gana X", Toast.LENGTH_LONG).show();
                                gameOver = true;
                            }
                            break;
                            case -1: {
                                Toast.makeText(MainActivity.this, "Gana O", Toast.LENGTH_LONG).show();
                                gameOver = true;
                            }
                            break;
                            case 0:
                                if (casillas_disponibles == 0)
                                    Toast.makeText(MainActivity.this, "EMPATE", Toast.LENGTH_LONG).show();
                                break;

                        }
                    }
                });
            }
        }
        nuevoJuego = (Button) findViewById(R.id.button10);
        nuevoJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                casillas_disponibles = 9;

                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero.length; j++) {
                        tablero[i][j] = "-";
                        tableroB[i][j].setText("");
                    }
                }
            }
        });
    }

    public int miniMax(int nivel, int casillas_disponibles, boolean x, String[][] tablero) {

        ArrayList<Integer> puntajes = new ArrayList<>();
        /**
         * casos base de terminación del algoritmo
         */
        if (casillas_disponibles == 0) {
            return hayGanador();
        }
        if (casillas_disponibles <= 4) {
            int retorno = hayGanador();
            if (retorno == 1 || retorno == -1) {
                return retorno;
            }
        }
        /*fin de casos base de terminacion del algoritmo*/

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j] == "-") {

                    tablero[i][j] = x ? "x" : "0";
                    puntajes.add(miniMax(nivel + 1, casillas_disponibles - 1, !x, tablero));
                    tablero[i][j] = "-";

                }

            }

        }

        if (nivel == 1) {
            this.casillas_disponibles--;
            int contador_posicion = 0;
            int mejor = 1;
            for (Integer i : puntajes) {

                if (i < mejor) {
                    mejor = i;
                }
            }

            for (Integer i : puntajes) {

                if (i == mejor) {

                    break;
                }
                contador_posicion++;
            }
            int contador = 0;
            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero.length; j++) {
                    if (tablero[i][j] == "-") {
                        if (contador_posicion == contador) {
                            tablero[i][j] = "0";
                            tableroB[i][j].setText("O");
                        }
                        contador++;

                    }

                }

            }

        }
        Collections.sort(puntajes);
        if (x) {
           return puntajes.get(puntajes.size() - 1);
        } else {
            return puntajes.get(0);
        }
    }

    public int hayGanador() {
        //gana filas
        if (tablero[0][0] == "x" && tablero[0][1] == "x" && tablero[0][2] == "x") {
            return 1;
        }
        if (tablero[1][0] == "x" && tablero[1][1] == "x" && tablero[1][2] == "x") {
            return 1;
        }
        if (tablero[2][0] == "x" && tablero[2][1] == "x" && tablero[2][2] == "x") {
            return 1;
        }
        //gana diagonales
        if (tablero[0][0] == "x" && tablero[1][1] == "x" && tablero[2][2] == "x") {
            return 1;
        }
        if (tablero[0][2] == "x" && tablero[1][1] == "x" && tablero[2][0] == "x") {
            return 1;
        }
        //gana columnas

        if (tablero[0][0] == "x" && tablero[1][0] == "x" && tablero[2][0] == "x") {
            return 1;
        }
        if (tablero[0][1] == "x" && tablero[1][1] == "x" && tablero[2][1] == "x") {
            return 1;
        }
        if (tablero[0][2] == "x" && tablero[1][2] == "x" && tablero[2][2] == "x") {
            return 1;
        }

        //gana filas
        if (tablero[0][0] == "0" && tablero[0][1] == "0" && tablero[0][2] == "0") {
            return -1;
        }
        if (tablero[1][0] == "0" && tablero[1][1] == "0" && tablero[1][2] == "0") {
            return -1;
        }
        if (tablero[2][0] == "0" && tablero[2][1] == "0" && tablero[2][2] == "0") {
            return -1;
        }
        //gana diagonales
        if (tablero[0][0] == "0" && tablero[1][1] == "0" && tablero[2][2] == "0") {
            return -1;
        }
        if (tablero[0][2] == "0" && tablero[1][1] == "0" && tablero[2][0] == "0") {
            return -1;
        }
        //gana columnas

        if (tablero[0][0] == "0" && tablero[1][0] == "0" && tablero[2][0] == "0") {
            return -1;
        }
        if (tablero[0][1] == "0" && tablero[1][1] == "0" && tablero[2][1] == "0") {
            return -1;
        }
        if (tablero[0][2] == "0" && tablero[1][2] == "0" && tablero[2][2] == "0") {
            return -1;
        }

        return 0;
    }
}
