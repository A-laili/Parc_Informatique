package controllers;

import entities.Machine;
import controllers.util.JsfUtil;
import controllers.util.JsfUtil.PersistAction;
import domains.MachineFacade;
import entities.Employe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "machineController")
@SessionScoped
public class MachineController implements Serializable {

    @EJB
    private domains.MachineFacade ejbFacade;
    private List<Machine> items = null;
    private Machine selected;
    private List<Machine> filteredItems = null;
    private Employe selectedEmployee;

    public MachineController() {
    }

    public Machine getSelected() {
        return selected;
    }

    public void setSelected(Machine selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MachineFacade getFacade() {
        return ejbFacade;
    }

    public Machine prepareCreate() {
        selected = new Machine();
        initializeEmbeddableKey();
        filteredItems = null;
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MachineCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MachineUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MachineDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Machine> getItems() {
        if (items == null) {
            items = getFacade().findAll();
            filteredItems = items;  // Initialize filteredItems with all items initially
        }

        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
                filteredItems = null;
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Machine> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Machine> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Machine.class)
    public static class MachineControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MachineController controller = (MachineController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "machineController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Machine) {
                Machine o = (Machine) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Machine.class.getName()});
                return null;
            }
        }

    }

    public List<Machine> getFilteredItems() {
         if (selectedEmployee != null) {
            // Filter items based on the selected employee
            filteredItems = items.stream()
                    .filter(machine -> machine.getEmploye().equals(selectedEmployee))
                    .collect(Collectors.toList());
            return filteredItems;
        }
        return items;
    }

    public void setFilteredItems(List<Machine> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public Employe getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employe selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }
    private Map<Integer, Integer> machinesData;

    @PostConstruct
    public void init() {
    // Chargez vos données de machine ici depuis la base de données ou ailleurs
        // Par exemple, items = machineService.getAllMachines();
        items = getFacade().findAll(); // Initialisez la liste des machines

        // Initialisez la carte de données des machines par année
        machinesData = new HashMap<>();
        calculateMachinesByYear();
    }

    public Map<Integer, Integer> getMachinesData() {
        return machinesData;
    }

    private void calculateMachinesByYear() {
        // Comptez le nombre de machines par année
        for (Machine machine : items) {
            int year = machine.getDateAchat().getYear(); // Assuming you have a Date property named 'dateAchat'
            machinesData.put(year, machinesData.getOrDefault(year, 0) + 1);
        }
    }

}
