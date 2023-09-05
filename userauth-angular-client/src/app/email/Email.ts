export class Email{
    id: string = '';
    toEmail: string = '';
    fromEmail: string = '';
    uploadUser: string = '';
    uploadDate: string = '';
    attachment!: BinaryData;
    fileName: string = '';
}