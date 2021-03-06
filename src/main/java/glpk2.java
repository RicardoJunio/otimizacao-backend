
import org.gnu.glpk.*;
import retrofit2.http.GET;
import retrofit2.http.POST;


import java.io.File;

public class glpk2 {

    static {
        try {
            File dll = new File("glpk_4_64.dll");
            File dll2 = new File ("glpk_4_64_java.dll");
            System.load(dll.getAbsolutePath());
            System.load(dll2.getAbsolutePath());
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }
    }

    public static void solution (){
        glp_prob lp;
        glp_iocp iocp;
        SWIGTYPE_p_int ind;
        SWIGTYPE_p_double val;
        int ret;


        lp = GLPK.glp_create_prob();
        System.out.println("Problem created");

        GLPK.glp_add_rows(lp, 3);

        GLPK.glp_set_row_name(lp, 1, "c1");
        GLPK.glp_set_row_bnds(lp, 1, GLPKConstants.GLP_UP, 0.0, 430.0);

        GLPK.glp_set_row_name(lp, 2, "c2");
        GLPK.glp_set_row_bnds(lp, 2, GLPKConstants.GLP_UP, 0.0, 460.0);

        GLPK.glp_set_row_name(lp, 3, "c3");
        GLPK.glp_set_row_bnds(lp, 3, GLPKConstants.GLP_UP, 0.0, 420.0);

        // Define columns
        GLPK.glp_add_cols(lp, 3);

        GLPK.glp_set_col_name(lp, 1, "x1");
        GLPK.glp_set_col_bnds(lp, 1, GLPKConstants.GLP_LO, 0.0, 0.0);
        GLPK.glp_set_col_kind(lp, 1, GLPKConstants.GLP_IV);

        GLPK.glp_set_col_name(lp, 2, "x2");
        GLPK.glp_set_col_bnds(lp, 2, GLPKConstants.GLP_LO, 0.0, 0.0);
        GLPK.glp_set_col_kind(lp, 2, GLPKConstants.GLP_IV);

        GLPK.glp_set_col_name(lp, 3, "x3");
        GLPK.glp_set_col_bnds(lp, 3, GLPKConstants.GLP_LO, 0.0, 0.0);
        GLPK.glp_set_col_kind(lp, 3, GLPKConstants.GLP_IV);



        // Allocate memory
        ind = GLPK.new_intArray(4);
        val = GLPK.new_doubleArray(4);

        GLPK.intArray_setitem(ind, 1, 1);
        GLPK.intArray_setitem(ind, 2, 2);
        GLPK.intArray_setitem(ind, 3, 3);
        GLPK.doubleArray_setitem(val, 1, 1);
        GLPK.doubleArray_setitem(val, 2, 2);
        GLPK.doubleArray_setitem(val, 3, 1);
        GLPK.glp_set_mat_row(lp, 1, 2, ind, val);


        GLPK.intArray_setitem(ind, 1, 1);
        GLPK.intArray_setitem(ind, 2, 2);
        GLPK.intArray_setitem(ind, 3, 3);
        GLPK.doubleArray_setitem(val, 1, 3);
        GLPK.doubleArray_setitem(val, 2, 0);
        GLPK.doubleArray_setitem(val, 3, 2);
        GLPK.glp_set_mat_row(lp, 2, 2, ind, val);


        GLPK.intArray_setitem(ind, 1, 1);
        GLPK.intArray_setitem(ind, 2, 2);
        GLPK.intArray_setitem(ind, 3, 3);
        GLPK.doubleArray_setitem(val, 1, 1);
        GLPK.doubleArray_setitem(val, 2, 4);
        GLPK.doubleArray_setitem(val, 3, 0);
        GLPK.glp_set_mat_row(lp, 3, 2, ind, val);

        GLPK.delete_doubleArray(val);
        GLPK.delete_intArray(ind);


        GLPK.glp_set_obj_name(lp, "obj");
        GLPK.glp_set_obj_dir(lp, GLPKConstants.GLP_MAX);
        //GLPK.glp_set_obj_coef(lp, 0, 0);
        GLPK.glp_set_obj_coef(lp, 1, 3);
        GLPK.glp_set_obj_coef(lp, 2, 2);
        GLPK.glp_set_obj_coef(lp, 3, 5);


        //  solve model - Integer Optimizer Method
        iocp = new glp_iocp();
        GLPK.glp_init_iocp(iocp);
        iocp.setPresolve(GLPKConstants.GLP_ON);
        //  GLPK.glp_write_lp(lp, null, "yi.lp");
        ret = GLPK.glp_intopt(lp, iocp);


        //  Retrieve solution
        if (ret == 0) {
            write_mip_solution(lp);
        }
        else {
            System.out.println("The problem could not be solved");
        };

        // free memory
        GLPK.glp_delete_prob(lp);
    }

    static void write_mip_solution(glp_prob lp)
    {
        int i;
        int n;
        String name;
        double val;

        name = GLPK.glp_get_obj_name(lp);
        val  = GLPK.glp_mip_obj_val(lp);
        System.out.print(name);
        System.out.print(" = ");
        System.out.println(val);
        n = GLPK.glp_get_num_cols(lp);
        for(i=1; i <= n; i++)
        {
            name = GLPK.glp_get_col_name(lp, i);
            val  = GLPK.glp_mip_col_val(lp, i);
            System.out.print(name);
            System.out.print(" = ");
            System.out.println(val);
        }
    }


    public static void main(String argv[])
    {
        System.out.print(GLPK.glp_version());
        solution();
    }



    public class GlpkApi {
    }

        @GET
        public String getResult(){
            return "Api Funciona";
        }

        @POST
        public String result (){
            solution();
            return "to aqui mano";
        }

    }




