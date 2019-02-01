/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.utils;

import com.mycompany.foreach.models.Mws98;
import java.io.File;
import java.io.IOException;
import java.util.List;
import com.mycompany.foreach.models.Nodo;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author 6_Delta
 */
public class GenerarXLS {

    private final Mws98 mwsno;
    private WritableSheet excelSheet;
    private WritableWorkbook xlsReport = null;
    private final List<String> logs;

    public GenerarXLS(Mws98 mwsno) {
        this.mwsno = mwsno;
        this.logs = new ArrayList<>();
    }

    public void escribirArchivo(String nombre, List<Nodo> nodos) {
        String ruta = this.mwsno.getToPath().toString() +"\\"+ nombre + 
                this.mwsno.getFinalextension();
        try {
            this.xlsReport = Workbook.createWorkbook( new File( ruta ) );
            this.excelSheet = this.xlsReport.createSheet("Reporte", 0);
            addHeads();
            for (int i = 0; i < nodos.size(); i++)
                for (int j = 0; j < nodos.get(i).obtenerLineas().size(); j++)
                    addBodyItem( nodos, i , j );
            addFooter( nodos);
        } catch (IOException | WriteException e) {
            this.logs.add("Error: " + e.getMessage());
        } finally {
            if (this.xlsReport != null) {
                try {
                    this.xlsReport.close();
                } catch (IOException e) {
                    this.logs.add("Error cerrando el xls: " + e.getMessage());
                }
            }
        }
    }

    private void addBodyItem(List<Nodo> nodos, int i, int j) throws WriteException {
        this.excelSheet.addCell(new Label(0, (3 * i) + (j + 1), nodos.get(i).getNode()) );
        this.excelSheet.addCell(new Label(1, (3 * i) + (j + 1), nodos.get(i).getRuntime()));
        this.excelSheet.addCell(new Label(2, (3 * i) + (j + 1), nodos.get(i).getStatus()));
        this.excelSheet.addCell(new Label(3, (3 * i) + (j + 1), nodos.get(i).obtenerLinea(j).getDisplayName()));
        this.excelSheet.addCell(new Number(4, (3 * i) + (j + 1), nodos.get(i).obtenerLinea(j).getValue()));
        this.excelSheet.addCell(new Number(5, (3 * i) + (j + 1), nodos.get(i).obtenerLinea(j).getMarginalValue()));
        this.excelSheet.addCell(new Number(6, (3 * i) + (j + 1), nodos.get(i).obtenerLinea(j).getCriticalValue()));
        this.excelSheet.addCell(new Number(7, (3 * i) + (j + 1), nodos.get(i).obtenerLinea(j).getMaxValue()));
        this.excelSheet.addCell(new Label(8, (3 * i) + (j + 1), nodos.get(i).getTime()));
    }

    private void addFooter(List<Nodo> nodos) throws WriteException, IOException {
        this.excelSheet.addCell(new Label(3, 3 * nodos.size() + 1, "Promedios"));
        this.excelSheet.addCell(new Formula(4, 3 * nodos.size() + 1, "PROMEDIO(E2:E" + ((3 * nodos.size()) + 1) + ")"));
        this.excelSheet.addCell(new Formula(5, 3 * nodos.size() + 1, "PROMEDIO(F2:F" + ((3 * nodos.size()) + 1) + ")"));
        this.excelSheet.addCell(new Formula(6, 3 * nodos.size() + 1, "PROMEDIO(G2:G" + ((3 * nodos.size()) + 1) + ")"));
        this.excelSheet.addCell(new Formula(7, 3 * nodos.size() + 1, "PROMEDIO(H2:H" + ((3 * nodos.size()) + 1) + ")"));
        this.xlsReport.write();
    }

    private void addHeads() throws WriteException {
        this.excelSheet.addCell(new Label(0, 0, "Node Alias"));
        this.excelSheet.addCell(new Label(1, 0, "Runtime Component"));
        this.excelSheet.addCell(new Label(2, 0, "Runtime Status"));
        this.excelSheet.addCell(new Label(3, 0, "Display Name"));
        this.excelSheet.addCell(new Label(4, 0, "Value"));
        this.excelSheet.addCell(new Label(5, 0, "Marginal Value"));
        this.excelSheet.addCell(new Label(6, 0, "Critical Value"));
        this.excelSheet.addCell(new Label(7, 0, "Max Value"));
        this.excelSheet.addCell(new Label(8, 0, "Time"));
        
    }
}
