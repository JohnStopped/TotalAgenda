private static List<Event> processListEvents(String body) {

	/*
	{
	   	"state": 1, 
	   	"desc": "List of events recovered successfully", 
	   	"list_events": 
	      	[
		        {
		          "event_id": 41618, 
		          "date": "2022-05-27 17:00:00", 
		          "advice_date": null, 
		          "name": "Hacer trabajo RSSA", 
		          "color": "red", 
		          "note": "si ome"
		        }, 
		        {
		          "event_id": 47229, 
		          "date": "2022-05-27 17:00:00", 
		          "advice_date": null, 
		          "name": "Hacer TFG", 
		          "color": "red", 
		          "note": ""
		        }, 

	    	]
	}

	*/
	List<Event> list = null;

	String in_body = body.substring(1,body.lenght()-1); //delete first keys

	String[] json_att = in_body.split(","); //divide in every attribute: value

	int state = new Integer(json_att[0].split(":")[1].trim()).intValue(); //Recover state

	if (state == 1) {
		String list_events = in_body.substring(in_body.indexOf('[')+1, in_body.indexOf(']')-1);
		
		List<String> events = new ArrayList<String>();

		boolean flag = true;
		int keyUp;
		int keyDown;
		int indexOffset=0;

		while (flag) {

			keyUp = list_events.indexOf('{', indexOffset);
			keyDown = list_events.indexOf('}', indexOffset);

			if (keyUp != -1 && keyDown!=-1){
				events.add(list_events.substring(keyUp+1,keyDown-1));
				indexOffset = keyDown;
			}
			else {
				flag = false;
			}
			list_events.substring(lis)
		}

		int event_id;
		String str_date;
		String str_advice_date;
		String name;
		String color;				
		String note;
		String[] date_pt;
		String[] advice_date_pt;
		GregorianCalendar date = "2022-05-27 17:00:00"; 
		GregorianCalendar advice_date = null;

		for (String str_event : events) {
			String[] att_value = str_event.split(',');

			event_id = new Integer(att_value[0].split(':')[1].trim()).intValue();

			str_date = att_value[1].split(':')[1].trim();
			str_date = str_date.substring(1,str_date.lenght()-1);
			date_pt = str_date.split(' ');

			str_advice_date = att_value[2].split(':')[1].trim();
			str_advice_date = str_advice_date.substring(1,str_advice_date.lenght()-1);
			advice_date_pt = str_advice_date.split(' ');

			name = att_value[3].split(':')[1].trim();
			name = name.substring(1,name.lenght()-1); 

		    color = att_value[4].split(':')[1].trim();
		    color = color.substring(1,color.lenght()-1);

		    note = att_value[5].split(':')[1].trim();
		    if (note.lenght() > 2)
		    	note = note.substring(1,note.lenght()-1);
		    else 
		    	note = null;

			date = new GregorianCalendar(new Integer(date_pt[0].split('-')[0]).intValue(),new Integer(date_pt[0].split('-')[1]).intValue(),new Integer(date_pt[0].split('-')[2]).intValue(),new Integer(date_pt[1].split('-')[0]).intValue(),new Integer(date_pt[1].split('-')[1]).intValue(),new Integer(date_pt[1].split('-')[2]).intValue());
			if (str_advice_date.contentEquals("null")) {
				advice_date = new GregorianCalendar(new Integer(advice_date_pt[0].split('-')[0]).intValue(),new Integer(advice_date_pt[0].split('-')[1]).intValue(),new Integer(advice_date_pt[0].split('-')[2]).intValue(),new Integer(advice_date_pt[1].split('-')[0]).intValue(),new Integer(advice_date_pt[1].split('-')[1]).intValue(),new Integer(advice_date_pt[1].split('-')[2]).intValue());
			}
			else {
				advice_date = null;
			} 
		    
		    
			Event obj_event = new Event(event_id,usuario.getEmail(),date,advice_date,name,color,note);
		}		

	}

	return list;

}