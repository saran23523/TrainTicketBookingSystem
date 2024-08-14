import java.util.*;
class Passenger
{
    public String name;
    public int age;
    public String compartment;
    public String seat_pref;
    public String ticket_type;
    
    Scanner in = new Scanner(System.in);
    
    public void get_passenger_details()
    {
        System.out.println("Enter the name : ");
        name=in.nextLine();
        System.out.println("Enter the age : ");
        age=in.nextInt();
        in.nextLine();
        System.out.println("Enter the compartment : ");
        compartment=in.nextLine();
        System.out.println("Enter the seat_pref : ");
        seat_pref=in.nextLine();
    }
}
class Compartment
{
    int confirmed,rac,wait_list;
    Compartment(int c,int r,int w)
    {
        confirmed=c;
        rac=r;
        wait_list=w;
    }
}
class Ticket
{
    public Passenger[] arr;
    public Compartment type;
    public int n;
    public static int id=100;
    public int ticket_id;
    
    
    Ticket(Passenger[] ar, int n, Compartment type) {
        this.n = n;
        this.ticket_id=id++;
        this.type=type;
        arr = new Passenger[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new Passenger();
            arr[i].name = ar[i].name;
            arr[i].age = ar[i].age;
            arr[i].compartment = ar[i].compartment;
            arr[i].seat_pref = ar[i].seat_pref;
            if(type.confirmed!=0)
            {
                arr[i].ticket_type="Confirmed";
                type.confirmed--;
            }
            else if(type.rac!=0)
            {
                arr[i].ticket_type="RAC";
                type.rac--;
            }
            else
            {
                arr[i].ticket_type="Waiting List";
                type.wait_list--;
            }
        }
    }

    public void display_ticket()
    {
        for(Passenger p : arr)
        {
            System.out.println("---------------------------------------------");
            System.out.println("Name: " + p.name);
            System.out.println("Age: " + p.age);
            System.out.println("Preference: " + p.compartment);
            System.out.println("Coach: " + p.seat_pref);
            System.out.println("Ticket Type: " + p.ticket_type);
            System.out.println("Ticket ID: "+ ticket_id);
        }
    }
    
    public void delete_ticket(int input_id) 
    {
        for(int i = 0; i < arr.length; i++) 
        {
            if(arr[i] != null && ticket_id == input_id) 
            {
                System.out.println("Deleting ticket ID: " + input_id);
                if(arr[i].ticket_type.equals("Confirmed")) 
                {
                    type.confirmed++;
                    if (type.rac > 0) 
                    {
                        promote_ticket("RAC", "Confirmed");
                    }
                } 
                else if(arr[i].ticket_type.equals("RAC")) 
                {
                    type.rac++;
                    if(type.wait_list > 0) 
                    {
                        promote_ticket("Waiting List", "RAC");
                    }
                } 
                else if(arr[i].ticket_type.equals("Waiting List")) 
                {
                    type.wait_list++;
                }
                arr[i] = null;
                for(int j = i; j < arr.length - 1; j++) 
                {
                    arr[j] = arr[j + 1];
                }
                arr[arr.length - 1] = null;
                System.out.println("Ticket deleted.");
                break;
            }
        }
    }

    private void promote_ticket(String fromType, String toType) 
    {
        for(Passenger p : arr) 
        {
            if(p != null && p.ticket_type.equals(fromType)) 
            {
                p.ticket_type = toType;
                if(fromType.equals("RAC")) 
                {
                    type.rac--;
                    type.confirmed++;
                }
                else if(fromType.equals("Waiting List")) 
                {
                    type.wait_list--;
                    type.rac++;
                }
                System.out.println("Promoted " + fromType + " to " + toType);
                break;
            }
        }
    }
}


public class TrainBookingSystem
{
    public static void main(String args[])
    {
        int ch,t,c,rac,w,input_id;
        Scanner in = new Scanner(System.in);
        List<Ticket> tickets = new ArrayList<>();
        System.out.print("Enter ticket count for Confirmed : ");
        c=in.nextInt();
        System.out.print("Enter ticket count for RAC : ");
        rac=in.nextInt();
        System.out.print("Enter ticket count for Waiting list : ");
        w=in.nextInt();
        Compartment c1=new Compartment(c,rac,w);
        while(true)
        {
            System.out.println("---------------------------------------------");
            System.out.print("1 -> To book ticket\n2 -> To display ticket\n3 -> To delete the ticket\n");
            System.out.print("Enter your choice : ");
            ch=in.nextInt();
            switch(ch)
            {
                case 1:
                {
                    System.out.print("Enter the no of tickets to book : ");
                    t=in.nextInt();
                    Passenger[] p=new Passenger[t];
                    for(int i=0;i<t;i++)
                    {
                        p[i] = new Passenger();
                        p[i].get_passenger_details();
                    }
                    Ticket t1 = new Ticket(p, t, c1);
                    tickets.add(t1);
                    break;
                }
                case 2:
                {
                    if(!tickets.isEmpty())
                    {
                        for(Ticket ticket : tickets)
                        {
                            ticket.display_ticket();
                        }
                    } 
                    else
                    {
                        System.out.println("No tickets booked yet.");
                    }
                    break;
                }
                case 3: 
                {
                    System.out.print("Enter the ID to delete: ");
                    input_id = in.nextInt();
                    boolean found = false;
                    for (Ticket ticket : tickets) {
                        if (ticket.ticket_id == input_id) 
                        {
                            ticket.delete_ticket(input_id);
                            tickets.remove(ticket);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Ticket ID not found.");
                    }
                    break;
                }
            }
        }
    }
}