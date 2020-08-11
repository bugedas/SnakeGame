/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gyvatele;

/**
 *
 * @author buged
 */
public class BendrMazgas<Tipas> {
    
    
        public Tipas Duomenys;
        public BendrMazgas<Tipas> Kitas;
        public BendrMazgas<Tipas> Atgal;
        public BendrMazgas(Tipas duomenys, BendrMazgas<Tipas> adresas, BendrMazgas<Tipas> atgal)
        {
            this.Duomenys = duomenys;
            this.Kitas = adresas;
            this.Atgal = atgal;
        }
    
}
