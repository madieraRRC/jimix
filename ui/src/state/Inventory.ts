import {observable} from "mobx";
import {MBean} from "./MBean";
import {Domain} from "./Domain";


export class Inventory {
	@observable domains: Domain[] = [];
	@observable mainClass: String = "";
	@observable hostName: String = "";
	@observable userName: String = "";
}
