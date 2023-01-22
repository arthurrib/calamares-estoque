import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFornecedor, NewFornecedor } from '../fornecedor.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFornecedor for edit and NewFornecedorFormGroupInput for create.
 */
type FornecedorFormGroupInput = IFornecedor | PartialWithRequiredKeyOf<NewFornecedor>;

type FornecedorFormDefaults = Pick<NewFornecedor, 'id'>;

type FornecedorFormGroupContent = {
  id: FormControl<IFornecedor['id'] | NewFornecedor['id']>;
  nome: FormControl<IFornecedor['nome']>;
  telefone: FormControl<IFornecedor['telefone']>;
};

export type FornecedorFormGroup = FormGroup<FornecedorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FornecedorFormService {
  createFornecedorFormGroup(fornecedor: FornecedorFormGroupInput = { id: null }): FornecedorFormGroup {
    const fornecedorRawValue = {
      ...this.getFormDefaults(),
      ...fornecedor,
    };
    return new FormGroup<FornecedorFormGroupContent>({
      id: new FormControl(
        { value: fornecedorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nome: new FormControl(fornecedorRawValue.nome, {
        validators: [Validators.required],
      }),
      telefone: new FormControl(fornecedorRawValue.telefone, {
        validators: [Validators.required],
      }),
    });
  }

  getFornecedor(form: FornecedorFormGroup): IFornecedor | NewFornecedor {
    return form.getRawValue() as IFornecedor | NewFornecedor;
  }

  resetForm(form: FornecedorFormGroup, fornecedor: FornecedorFormGroupInput): void {
    const fornecedorRawValue = { ...this.getFormDefaults(), ...fornecedor };
    form.reset(
      {
        ...fornecedorRawValue,
        id: { value: fornecedorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FornecedorFormDefaults {
    return {
      id: null,
    };
  }
}
