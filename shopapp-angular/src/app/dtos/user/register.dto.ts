import {
  isString,
  isNotEmpty,
  isPhoneNumber,
  isDate,
  IsString,
  IsDate,
  IsPhoneNumber,
  IsNotEmpty,
} from 'class-validator';
export class RegisterDTO {
  @IsPhoneNumber()
  phone_number: string;
  @IsString()
  @IsNotEmpty()
  password: string;
  @IsString()
  @IsNotEmpty()
  retype_password: string;
  @IsString()
  fullname: string;
  @IsString()
  @IsNotEmpty()
  address: string;
  google_account_id: number = 0;
  facebook_account_id: number = 0;
  role_id: number = 1;
  @IsDate()
  date_of_birth: Date;
  constructor(data: any) {
    this.phone_number = data.phone_number;
    this.password = data.password;
    this.retype_password = data.retypePassword;
    this.fullname = data.fullname;
    this.address = data.address;
    this.date_of_birth = data.date_of_birth;
    this.google_account_id = data.google_account_id || 0;
    this.facebook_account_id = data.facebook_account_id || 0;
    this.role_id = data.role_id || 1;
  }
}
