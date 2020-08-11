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





public class DoubleLinkedList<Tipas>
    {

        private BendrMazgas<Tipas> pr; // sąrašo pradžia
        private BendrMazgas<Tipas> pb; // sąrašo pabaiga
        private BendrMazgas<Tipas> d; // sąrašo sąsaja

        public DoubleLinkedList()
        {
            
            this.pr = null;
            this.d = null;
            this.pb = null;
        }

        public void Pradzia()
        {
            d = pr;
        }

        public void Kitas()
        {
            d = d.Kitas;
        }
        
        public void Atgal()
        {
            d = d.Atgal;
        }
        
        public boolean Yra()
        {
            return d != null;
        }

        public void DetiDuomenisA(Tipas inf)
        {

            BendrMazgas<Tipas> dd = new BendrMazgas<Tipas>(inf, null, pb);
            if (pr != null)
            {
                pb.Kitas = dd;
            }
            else
            {
                pr = dd;
            }
            pb = dd;
        }

        public Tipas ImtiDuomenis()
        {
            return d.Duomenys;
        }
        
        public int Dydis(){
            d = pr;
            int z = 0;
            
            while(d != null){
                z++;
                d = d.Kitas;
                
                
            }
            
            return z;
            
        }



    }
